package com.hunar.api.service.impl;

import com.hunar.api.bean.CustomerBean;
import com.hunar.api.bean.InvoiceBean;
import com.hunar.api.bean.OrderBean;
import com.hunar.api.bean.TypeMeasurementBean;
import com.hunar.api.constant.Constants;
import com.hunar.api.email.EmailRequest;
import com.hunar.api.email.EmailResponse;
import com.hunar.api.email.EmailService;
import com.hunar.api.entity.*;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.repository.*;
import com.hunar.api.service.MeasurementService;
import com.hunar.api.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.data.domain.Pageable;

@Service
public class OrderServiceImpl implements OrderService {

    public static Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private static final String PREFIX = "ORD-";
    private static final String DATE_FORMAT = "yyyyMMdd";
    private final AtomicInteger counter = new AtomicInteger(1);  // Atomic counter for uniqueness
    private String currentDate = getCurrentDate();
    private final Map<String, AtomicInteger> orderNumberCache = new ConcurrentHashMap<>();

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookingMeasureMappingRepository bmmRepo;

    @Autowired
    MeasurementService measurementService;

    @Autowired
    EmailService emailService;


    //    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${company.mobile.number}")
    private String companyMobileNo;

    @Value("${cron.jobs.time}")
    private static final String cronJobs = "0 0 23 * * *";

    @Override
    public OrderBean createOrder(OrderBean orderBean) throws FmkException, IOException {
        logger.info("Creating new order: " + orderBean.toString());
        Order orderEntity = new Order();
        BeanUtils.copyProperties(orderBean, orderEntity);
        orderEntity.setOrderNo(generateUniqueOrderId());
        CustomerEntity customer = checkByCustomerId(orderBean.getIdCustomer());
        orderEntity.setCustomer(customer);
        orderEntity.setBookingDate(LocalDate.now());
        orderEntity.setIdCustomer(orderBean.getIdCustomer());
        orderEntity.setCustomerName(customer.getCustomerName());
        orderEntity.setDeliveryDate(orderBean.getDeliveryDate());
        orderEntity.setActualDeliveryDate(orderBean.getDeliveryDate());

        // If there is an image file, store it
//        MultipartFile imageFile = orderBean.getImage();
//        if (imageFile != null && !imageFile.isEmpty()) {
//            String imagePath = storeImage(imageFile);
//            orderEntity.setImage(imagePath.getBytes()); // Store file path in the DB (if using file system)
//        }

        orderEntity.setOrderStatus(Constants.IN_PROGRESS);
        orderEntity.setPaymentStatus(Constants.UNPAID);
       orderEntity= orderRepository.save(orderEntity);
      List<TypeMeasurementBean> typeMeasurementBeans = createMapping(orderEntity,orderBean);
        logger.info("Created new order successfully: " + orderBean.toString());
        OrderBean orderBean1 = new OrderBean();
        BeanUtils.copyProperties(orderEntity,orderBean1);
        orderBean1.setTypeMeasurementBeans(typeMeasurementBeans);
        orderBean1.setCustomerName(orderEntity.getCustomerName());

        //sending email to customer
        sendEmail(orderBean1);
        return  orderBean1;
    }

    private void sendEmail(OrderBean orderBean1) {
        CustomerEntity customerEntity = customerRepository.findById(orderBean1.getIdCustomer()).get();
       StringJoiner items = new StringJoiner(",");
        for (TypeMeasurementBean typeMeasurement: orderBean1.getTypeMeasurementBeans()){
            items.add(typeMeasurement.getTypeName());
        }
        EmailRequest request = new EmailRequest();
        request.setTo(customerEntity.getCustomerEmail());
        Map<String, Object> model = new HashMap<>();
        model.put("name", orderBean1.getCustomerName());
        model.put("to",customerEntity.getCustomerEmail());
        model.put("date", new Date().toString());
        model.put("orderNo", orderBean1.getOrderNo());
        model.put("sdate", orderBean1.getBookingDate().toString());
        model.put("edate", orderBean1.getDeliveryDate().toString());
        model.put("items", items);
        model.put("mobile", companyMobileNo);
         emailService.sendEmail(request, model);

    }


    private List<TypeMeasurementBean> createMapping(Order orderEntity, OrderBean orderBean) {
        bmmRepo.deleteAllByIdOrder(orderEntity.getOrderId());
        List<TypeMeasurementBean> typeMeasurementBeanList = new ArrayList<>();
        if (!orderBean.getIdMeasurements().isEmpty()){
            for (Integer idMeasure : orderBean.getIdMeasurements()){
                BookingMeasuremntMapping bmmEntity = new BookingMeasuremntMapping();
                bmmEntity.setIdOrder(orderEntity.getOrderId());
                bmmEntity.setIdTypeMeasurement(idMeasure);
                bmmRepo.save(bmmEntity);
                logger.info("Data save in mapping table");
                typeMeasurementBeanList.add(measurementService.getMeasurementById(idMeasure));
            }
        }

        return  typeMeasurementBeanList;
    }

//    private String storeImage(MultipartFile file) throws IOException {
//        // Create the directory if it doesn't exist
//        File directory = new File(uploadDir);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//
//        // Generate file name and path
//        String fileName = file.getOriginalFilename();
//        Path filePath = Paths.get(uploadDir, fileName);
//
//        // Copy the image to the directory
//        Files.copy(file.getInputStream(), filePath);
//
//        // Return the file path (this can be stored in the database)
//        return filePath.toString();
//    }


    private Address checkByAddressId(int idAddress) throws FmkException {
        Optional<Address> address = addressRepository.findById(idAddress);
        if (!address.isPresent()) {
            throw new FmkException("A1002","Invalid Address Id: "+String.valueOf(idAddress));
        }
        return address.get();
    }

    private CustomerEntity checkByCustomerId(int idCustomer) throws FmkException {
        Optional<CustomerEntity> customer = customerRepository.findById(idCustomer);
        if (!customer.isPresent()) {
            throw new FmkException("C1001", "Invalid customer Id: "+String.valueOf(idCustomer));
        }
        return customer.get();
    }

    private String generateOrderNumber() {
        String todayDate = getCurrentDate();

        // Reset the counter if the date has changed
        if (!todayDate.equals(currentDate)) {
            counter.set(1); // reset counter for new day
            currentDate = todayDate;
        }

        // Format the order number
        String orderNumber = String.format("%s%s-%04d", PREFIX, todayDate, counter.getAndIncrement());
        Order order = orderRepository.findByOrderNo(orderNumber);
        if (order!=null){
           return orderNumber = incrementOrderId(orderNumber);

        }
        return orderNumber;
    }

    public  String incrementOrderId(String orderId) {
        // Split the order ID by the last dash
        int lastDashIndex = orderId.lastIndexOf('-');
        String prefix = orderId.substring(0, lastDashIndex + 1); // includes the dash
        String numberPart = orderId.substring(lastDashIndex + 1); // e.g., "0003"

        // Parse the number part, increment, and format it back with leading zeros
        int number = Integer.parseInt(numberPart);
        number++; // increment by 1
        String newNumberPart = String.format("%04d", number); // always 4 digits

        // Combine back
        return prefix + newNumberPart;
    }

    public synchronized String generateUniqueOrderId() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "ORD-" + date + "-";

        // Load or initialize counter
        AtomicInteger counter = orderNumberCache.computeIfAbsent(date, d -> {
            // Query DB for latest order today
            Pageable limitOne = PageRequest.of(0, 1);
            List<String> latest = orderRepository.findLatestOrderNoByDate(d, limitOne);

            int lastNumber = 0;
            if (!latest.isEmpty()) {
                String lastOrderId = latest.get(0);
                String numberPart = lastOrderId.substring(lastOrderId.lastIndexOf('-') + 1);
                lastNumber = Integer.parseInt(numberPart);
            }

            return new AtomicInteger(lastNumber);
        });

        String newOrderId;

        // Keep trying until we find a unique one (safety net)
        do {
            int nextNumber = counter.incrementAndGet();
            newOrderId = prefix + String.format("%04d", nextNumber);
        } while (orderRepository.existsByOrderNo(newOrderId));

        return newOrderId;
    }

    // Helper method to get current date in the desired format
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date()); // Use current date
    }

    @Override
    public OrderBean updateOrder(OrderBean orderBean) throws FmkException {
        if (orderBean != null && orderBean.getOrderId() != 0) {
            logger.info("Updating order: " + orderBean.getOrderId());
            Optional<Order> orderEntity = orderRepository.findById(orderBean.getOrderId());
            if (!orderEntity.isPresent()) {
                logger.info("Order does not exists with OrderName: " + orderBean.getOrderId());
                throw new FmkException("O1001","Order does not exists with Order Id: " + String.valueOf(orderBean.getOrderId()));
            }
            orderEntity.get().setOrderId(orderBean.getOrderId());
            orderEntity.get().setAmount(orderBean.getAmount());
            orderEntity.get().setBookingDate(orderBean.getBookingDate());
            orderEntity.get().setComments(orderBean.getComments());
            orderEntity.get().setDeliveryDate(orderBean.getDeliveryDate());
            orderEntity.get().setQuantity(orderBean.getQuantity());
//            orderEntity.get().setType(orderBean.getType());
            orderEntity.get().setIdCustomer(orderBean.getIdCustomer());
            orderEntity.get().setOrderStatus(orderEntity.get().getOrderStatus());
//            CustomerEntity customer = checkByCustomerId(orderBean.getIdCustomer());
           Order order = orderRepository.save(orderEntity.get());
            logger.info("Updated order successfully: " + order.getOrderId());
            List<TypeMeasurementBean> typeMeasurementBeans = createMapping(order,orderBean);
            OrderBean orderBean1 = new OrderBean();
            BeanUtils.copyProperties(order,orderBean1);
            orderBean1.setTypeMeasurementBeans(typeMeasurementBeans);
            orderBean1.setCustomerName(order.getCustomerName());
            return orderBean1;
        }
        return null;
    }


    @Override
    public List<OrderBean> getListOfAllOrders() throws FmkException {
        List<OrderBean> listOfAllOrdersBean = null;
        List<Order> listOfAllOrdersEntity = (List<Order>) orderRepository.findAll();
        if (!listOfAllOrdersEntity.isEmpty()) {
            listOfAllOrdersBean = new ArrayList<>();
            for (Order orderEntity : listOfAllOrdersEntity) {
                OrderBean orderBean = new OrderBean();
                BeanUtils.copyProperties(orderEntity, orderBean);
//                CustomerEntity customer = checkByCustomerId(orderEntity.getIdCustomer());
//                orderBean.setCustomerName(customer.getCustomerName());
                List<TypeMeasurementBean> typeMeasurementBeans = getTypeMeasurementByOrderId(orderEntity.getOrderId());
               orderBean.setTypeMeasurementBeans(typeMeasurementBeans);
                listOfAllOrdersBean.add(orderBean);
            }
            return listOfAllOrdersBean;
        }
        else {
            return new ArrayList<>();
        }

    }

    private List<TypeMeasurementBean> getTypeMeasurementByOrderId(int orderId) {
        List<BookingMeasuremntMapping> bmmEntity = bmmRepo.findAllByIdOrder(orderId);
        List<TypeMeasurementBean> typeMeasurementBeanList = new ArrayList<>();
        if (!bmmEntity.isEmpty()){
            for (BookingMeasuremntMapping bookingMeasuremntMapping : bmmEntity){
               TypeMeasurementBean typeMeasurementBean= measurementService.getMeasurementById(bookingMeasuremntMapping.getIdTypeMeasurement());
               typeMeasurementBeanList.add(typeMeasurementBean);
            }
        }
        return typeMeasurementBeanList;
    }

    @Override
    public OrderBean getOrderById(int idOrder) throws FmkException {
        if (idOrder <= 0) {
            throw new FmkException("O1001", "Order does not exists with Order Id: " + String.valueOf(idOrder));
        }
        Optional<Order> orderEntity = orderRepository.findById(idOrder);
        if (!orderEntity.isPresent()) {
            throw new FmkException("O1001","Order does not exists with Order Id: " + String.valueOf(idOrder));
        } else {
            OrderBean orderBean = new OrderBean();
            BeanUtils.copyProperties(orderEntity, orderBean);
            return orderBean;
        }
    }

    @Override
    public String deleteOrderById(int idOrder) throws FmkException {
        if (idOrder == 0) {
            logger.info("Invalid order ID: " + idOrder);
            throw new FmkException("O1002", "Invalid order ID: " + String.valueOf(idOrder));
        }
        bmmRepo.deleteAllByIdOrder(idOrder);
        orderRepository.deleteById(idOrder);
        return "Order deleted succesffuly";
    }

    @Override
    public List<CustomerBean> findBookingDate(LocalDate date) throws FmkException {
//        List<CustomerBean> customerBeans = null;
//        List<OrderBean> orderBeans = null;
//        List<CustomerEntity> customerEntities = null;
//        if (date == null) {
//            return customerService.getListOfAllCustomers();
//        } else {
//            customerBeans = new ArrayList<>();
//            Set<Integer> ids = new HashSet<>();
//            List<Order> orders = orderRepository.findbyBookingDate(date);
//            if (!orders.isEmpty()) {
//                for (Order order : orders) {
//                    ids.add(order.getIdCustomer());
//                  }
//                for (Integer id : ids){
//                    CustomerEntity customerEntity = customerRepository.findById(id).get();
//                    if (customerEntity!= null){
//                        customerEntities= new ArrayList<>();
//
//                    }
//                }
//                }
//            }

        return new ArrayList<>();
    }

    @Override
    public List<OrderBean> getListOfAllOrdersByCustomerId(int custId) throws FmkException {
        List<OrderBean> listOfAllOrdersBean = null;
        List<Order> listOfAllOrdersEntity = (List<Order>) orderRepository.findAllByIdCustomer(custId);
        if (!listOfAllOrdersEntity.isEmpty()) {
            listOfAllOrdersBean = new ArrayList<>();
            for (Order orderEntity : listOfAllOrdersEntity) {
                OrderBean orderBean = new OrderBean();
                BeanUtils.copyProperties(orderEntity, orderBean);
//                CustomerEntity customer = checkByCustomerId(orderEntity.getIdCustomer());
//                orderBean.setCustomerName(customer.getCustomerName());
                List<TypeMeasurementBean> typeMeasurementBeans = getTypeMeasurementByOrderId(orderEntity.getOrderId());
                orderBean.setTypeMeasurementBeans(typeMeasurementBeans);
                listOfAllOrdersBean.add(orderBean);
            }
            return listOfAllOrdersBean;
        }
       return new ArrayList<>();
    }

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public InvoiceBean getInvoiceDetails(String orderNo) {
        if (orderNo != null){
            Order order = orderRepository.findByOrderNo(orderNo);
            if (order != null){
                CustomerEntity customerEntity = customerRepository.findById(order.getIdCustomer()).get();
                if (customerEntity!=null){
                    CompanyEntity companyEntity = companyRepository.findAll().get(0);
                    InvoiceBean invoiceBean = new InvoiceBean();
                    invoiceBean.setOrderNo(orderNo);
                    invoiceBean.setItem(order.getType());
                    invoiceBean.setTodaysDate(LocalDate.now());
                    invoiceBean.setAmount(String.valueOf(order.getAmount()));
                    invoiceBean.setTotalamt(String.valueOf(order.getTotalAmt()));
                    invoiceBean.setQty(String.valueOf(order.getQuantity()));
                    invoiceBean.setSerialNo(1);
                    invoiceBean.setCustomerName(customerEntity.getCustomerName());
                    invoiceBean.setCompanyAddress(companyEntity.getCompanyAddress());
                    invoiceBean.setCompanyMblNo(companyEntity.getCompanyMblNo());
                    invoiceBean.setCompanyName(companyEntity.getCompanyName());
                    invoiceBean.setCity(companyEntity.getCity());
                    invoiceBean.setPincode(companyEntity.getPincode());
                    invoiceBean.setBookingDate(order.getBookingDate());
                    return invoiceBean;

                }
         }
        }
        return null;
    }

    @Override
    public OrderBean orderDelivered(int idOrder) {
        Optional<Order> order = orderRepository.findById(idOrder);
        if (order.isPresent()){
            order.get().setOrderStatus(Constants.DELIVERED);
            order.get().setActualDeliveryDate(LocalDate.now());
           Order order1 = orderRepository.save(order.get());
           OrderBean orderBean = new OrderBean();
           BeanUtils.copyProperties(order1, orderBean);
           return  orderBean;
        }
        return  null;
    }

    @Override
    public OrderBean orderCompleted(int idOrder) {
        Optional<Order> order = orderRepository.findById(idOrder);
        if (order.isPresent()){
            order.get().setOrderStatus(Constants.COMPLETED);
            order.get().setCompletionDate(LocalDate.now());
            Order order1 = orderRepository.save(order.get());
            OrderBean orderBean = new OrderBean();
            BeanUtils.copyProperties(order1, orderBean);
            return  orderBean;
        }
        return  null;
    }

    @Override
    public OrderBean orderAlteration(int idOrder, String alterComments) {
        Optional<Order> order = orderRepository.findById(idOrder);
        if (order.isPresent()){
            order.get().setOrderStatus(Constants.ALTERATION);
            order.get().setAlterComments(alterComments);
            Order order1 = orderRepository.save(order.get());
            OrderBean orderBean = new OrderBean();
            BeanUtils.copyProperties(order1, orderBean);
            return  orderBean;
        }
        return  null;

    }

    @Autowired
    UserRepository userRepository;

    @Scheduled(cron = cronJobs)
    @Override
    public void schedulerForOrderDelivery() {
        LocalDate targetDate = LocalDate.now().plusDays(4);
        List<Order> orderList = orderRepository.findOrdersWithDeliveryDate(targetDate);
        List<UserEntity> userEntities = userRepository.findAllByRoles("ROLE_ADMIN");
        StringJoiner items = new StringJoiner(",");
        for (Order order: orderList){
            items.add(order.getOrderNo());
        }

        Map<String, Object> model = new HashMap<>();
        model.put("date", new Date().toString());
        model.put("deliveryDate", targetDate);
        model.put("edate", orderList.get(0).getDeliveryDate().toString());
        model.put("items", items);
        for (UserEntity userEntity: userEntities){
            emailService.sendEmailForDelivery(userEntity.getUserEmail(), model);
        }


    }
}
