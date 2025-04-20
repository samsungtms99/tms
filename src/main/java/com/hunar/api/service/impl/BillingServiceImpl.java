package com.hunar.api.service.impl;

import com.hunar.api.bean.BilingBean;
import com.hunar.api.bean.BillingInvoice;
import com.hunar.api.bean.BillingMapBean;
import com.hunar.api.bean.OrderBean;
import com.hunar.api.constant.Constants;
import com.hunar.api.entity.*;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.repository.*;
import com.hunar.api.service.BillingService;
import com.hunar.api.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BillingServiceImpl implements BillingService {

    public static Logger logger = LogManager.getLogger(BillingServiceImpl.class);

    private static final String PREFIX = "INV-";
    private static final String DATE_FORMAT = "yyyyMMdd";
    private final AtomicInteger counter = new AtomicInteger(1);  // Atomic counter for uniqueness
    private String currentDate = getCurrentDate();

    @Autowired
    BillingMapRepository billingMapRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    BillingRepository billingRepository;

    @Autowired
    OrderRepository orderRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class, FmkException.class, IOException.class, ArithmeticException.class, NullPointerException.class})
    @Override
    public BilingBean createBillForOrder(BilingBean billingBean) throws FmkException {
        logger.info("Billing creation started");
        if (billingBean==null){
            logger.info("Invalid request body");
            throw  new FmkException("BL1001", "Invalid request body");
        }if (billingBean.getIdOrder() <1){
            logger.info("Invalid Order Id");
            throw  new FmkException("BL1002", "Invalid Order Id");
        }
        if (billingBean.getIdCustomer()<1){
            logger.info("Invalid Customer Id");
            throw  new FmkException("BL1003", "Invalid Customer Id");
        }
        checkIfBillAlreadyCreated(billingBean);
        BilingBean billingBeans = new BilingBean();
        BillingEntity billingEntity = new BillingEntity();
        billingEntity.setIdOrder(billingBean.getIdOrder());
        billingEntity.setIdCustomer(billingBean.getIdCustomer());
        billingEntity.setOrderNo(orderRepository.findById(billingBean.getIdOrder()).get().getOrderNo());
        billingEntity.setAdvanceAmt(billingBean.getAdvanceAmt());
        billingEntity.setDiscountedAmt(billingBean.getDiscountedAmt());
        billingEntity.setTotalAmt(billingBean.getTotalAmt());
        billingEntity.setRemAmt(billingBean.getRemAmt());
        billingEntity.setActualAmt(billingBean.getActualAmt());
        billingEntity.setInvoiceNo(generateInvoiceNumber());
        billingEntity.setPaymentStatus(Constants.INCOMPLETE);
        billingEntity.setRemarks(billingBean.getRemarks());
       billingEntity = billingRepository.save(billingEntity);
        BeanUtils.copyProperties(billingEntity, billingBeans);
       if (billingEntity.getIdBill()>0 && !billingBean.getBillingMap().isEmpty()){
          List<BillingMapBean> billingMapBeans = createBillMap(billingEntity, billingBean.getBillingMap());
          billingBeans.setBillingMap(billingMapBeans);
       }
       logger.info("Billing creation completed");
       if (billingEntity.getRemAmt()==0 && billingEntity.getActualAmt() == billingEntity.getAdvanceAmt()){
           billingEntity.setPaymentStatus(Constants.COMPLETED);
          billingEntity = billingRepository.save(billingEntity);
          billingBeans.setPaymentStatus(billingEntity.getPaymentStatus());
         Optional<Order> order=orderRepository.findById(billingEntity.getIdOrder());
         if (order.isPresent()){
             order.get().setPaymentStatus(Constants.PAID);
             orderRepository.save(order.get());
         }

       }

        return billingBeans;
    }

    private void checkIfBillAlreadyCreated(BilingBean billingBean) throws FmkException {
        if (billingRepository.existsByIdOrderAndIdCustomer(billingBean.getIdOrder(), billingBean.getIdCustomer())){
            throw new FmkException("BL1004", "Bill already generetaed for this "+orderRepository.findById(billingBean.getIdOrder()).get().getOrderNo() +" order no.");
        }
    }


    private List<BillingMapBean> createBillMap(BillingEntity billingEntity, List<BillingMapBean> billingMap) {
       List<BillingMapBean> mapBeanList = new ArrayList<>();
        for (BillingMapBean billingMapBean : billingMap) {
            BillingMapEntity billingMapEntity = new BillingMapEntity();
            billingMapEntity.setIdBilling(billingEntity.getIdBill());
            billingMapEntity.setField(billingMapBean.getField());
            billingMapEntity.setValue(billingMapBean.getValue());
            billingMapEntity = billingMapRepository.save(billingMapEntity);
            BillingMapBean billingMapBean1 = new BillingMapBean();
            BeanUtils.copyProperties(billingMapEntity, billingMapBean1);
            mapBeanList.add(billingMapBean1);
        }
        return  mapBeanList;
    }

    private String generateInvoiceNumber() {
        String todayDate = getCurrentDate();

        // Reset the counter if the date has changed
        if (!todayDate.equals(currentDate)) {
            counter.set(1); // reset counter for new day
            currentDate = todayDate;
        }

        // Format the order number
        String orderNumber = String.format("%s%s-%04d", PREFIX, todayDate, counter.getAndIncrement());
        return orderNumber;
    }

    // Helper method to get current date in the desired format
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date()); // Use current date
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class, FmkException.class, IOException.class, ArithmeticException.class, NullPointerException.class})
    @Override
    public BilingBean updateBillForOrder(BilingBean billingBean) throws  FmkException {
        logger.info("Billing updation started");
        if (billingBean==null){
            logger.info("Invalid request body");
            throw  new FmkException("BL1001", "Invalid request body");
        }if (billingBean.getIdBill() <1){
            logger.info("Invalid Bill Id");
            throw  new FmkException("BL1005", "Invalid Bill Id");
        }if (billingBean.getIdOrder() <1){
            logger.info("Invalid Order Id");
            throw  new FmkException("BL1002", "Invalid Order Id");
        }
        if (billingBean.getIdCustomer()<1){
            logger.info("Invalid Customer Id");
            throw  new FmkException("BL1003", "Invalid Customer Id");
        }
        if (billingBean.getBillingMap().isEmpty()){
            logger.info("Invalid Billing params");
            throw  new FmkException("BL1003", "Billing params are required");
        }
        deleteBillingMap(billingBean.getIdBill());
        BilingBean billingBeans = new BilingBean();
        Optional<BillingEntity> billingEntityOptional = billingRepository.findById(billingBean.getIdBill());
        if(!billingEntityOptional.isPresent()){
            logger.info("Invalid Billing Id");
            throw  new FmkException("BL1005", "Invalid Billing Id");
        }
        BillingEntity billingEntity = billingEntityOptional.get();
        billingEntity.setIdOrder(billingBean.getIdOrder());
        billingEntity.setIdCustomer(billingBean.getIdCustomer());
        billingEntity.setOrderNo(orderRepository.findById(billingBean.getIdOrder()).get().getOrderNo());
        billingEntity.setAdvanceAmt(billingBean.getAdvanceAmt());
        billingEntity.setDiscountedAmt(billingBean.getDiscountedAmt());
        billingEntity.setTotalAmt(billingBean.getTotalAmt());
        billingEntity.setRemAmt(billingBean.getRemAmt());
        billingEntity.setActualAmt(billingBean.getActualAmt());
        billingEntity.setInvoiceNo(generateInvoiceNumber());
        billingEntity.setPaymentStatus(Constants.INCOMPLETE);
        billingEntity.setRemarks(billingBean.getRemarks());
        billingEntity = billingRepository.save(billingEntity);
        BeanUtils.copyProperties(billingEntity, billingBeans);
        if (billingEntity.getIdBill()>0 && !billingBean.getBillingMap().isEmpty()){
            List<BillingMapBean> billingMapBeans = createBillMap(billingEntity, billingBean.getBillingMap());
            billingBeans.setBillingMap(billingMapBeans);
        }
        logger.info("Billing creation completed");
        if (billingEntity.getRemAmt()==0 && billingEntity.getActualAmt() == billingEntity.getAdvanceAmt()){
            billingEntity.setPaymentStatus(Constants.COMPLETED);
            billingEntity = billingRepository.save(billingEntity);
            billingBeans.setPaymentStatus(billingEntity.getPaymentStatus());
            Optional<Order> order=orderRepository.findById(billingEntity.getIdOrder());
            if (order.isPresent()){
                order.get().setPaymentStatus(Constants.PAID);
                orderRepository.save(order.get());
            }

        }

        return billingBeans;
    }

    private void deleteBillingMap(int idBilling) {
       billingMapRepository.deleteByIdBilling(idBilling);
    }

    @Override
    public List<BilingBean> getListOfAllBills() {
       List<BilingBean> bilingBeanList = null;
       List<BillingEntity> billingEntityList = billingRepository.findAll();
       if (!billingEntityList.isEmpty()){
           bilingBeanList = new ArrayList<>();
           for (BillingEntity billingEntity : billingEntityList){
               BilingBean bilingBean = new BilingBean();
               BeanUtils.copyProperties(billingEntity, bilingBean);
               List<BillingMapBean> billingMapBeanList = fetchAllMapValuesByIdBill(billingEntity.getIdBill());
               bilingBean.setBillingMap(billingMapBeanList);
               bilingBeanList.add(bilingBean);
           }
       }
       return bilingBeanList;
    }

    private List<BillingMapBean> fetchAllMapValuesByIdBill(int idBill) {
        List<BillingMapBean> billingMapBeans = new ArrayList<>();
        List<BillingMapEntity> billingMapEntities = billingMapRepository.findAllByIdBilling(idBill);
        if (!billingMapEntities.isEmpty()){
            for (BillingMapEntity billingMapEntity:billingMapEntities){
                BillingMapBean billingMapBean = new BillingMapBean();
                BeanUtils.copyProperties(billingMapEntity, billingMapBean);
                billingMapBeans.add(billingMapBean);
            }
        }
        return billingMapBeans;
    }

    @Override
    public String deleteBillsById(int idBill) {
        if (idBill>0){
            billingMapRepository.deleteByIdBilling(idBill);
            billingRepository.deleteById(idBill);
            return " Billing deleted successfully..";
        }
        return "Some error occurred";
    }

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public BillingInvoice generateBillingInvoice(int idBill) throws FmkException {
        if (idBill<1){
            throw new FmkException("BL1006", "Invalid billing id");
        }
        Optional<BillingEntity> billingEntity = billingRepository.findById(idBill);
        CustomerEntity customerEntity = customerRepository.findById(billingEntity.get().getIdCustomer()).get();
        List<BillingMapEntity> items = billingMapRepository.findAllByIdBilling(billingEntity.get().getIdBill());
//        Order order = orderRepository.findByOrderNo(billingEntity.get().getOrderNo());
        CompanyEntity companyEntity = companyRepository.findAll().get(0);

        BillingInvoice billingInvoice = new BillingInvoice();
        billingInvoice.setBillingEntity(billingEntity.get());
        billingInvoice.setItems(items);
        billingInvoice.setCompanyEntity(companyEntity);
        billingInvoice.setCustomerName(customerEntity.getCustomerName());
//        billingInvoice.setOrder(order);

        return billingInvoice;
    }
}

