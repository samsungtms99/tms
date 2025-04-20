package com.hunar.api.controller;

import com.hunar.api.bean.CustomerBean;
import com.hunar.api.bean.InvoiceBean;
import com.hunar.api.bean.OrderBean;
import com.hunar.api.constant.Constants;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = Constants.CROSS_ORIGIN)
@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping(value = "/createOrder")
    public OrderBean createOrder(@RequestBody OrderBean orderBean) throws FmkException, IOException {
        if (orderBean!=null){
            return orderService.createOrder(orderBean);
        }
        return null;
    }

    @PostMapping(value = "/updateOrder")
    public OrderBean updateOrder(@RequestBody OrderBean orderBean) throws FmkException {
        if (orderBean!=null){
            return orderService.updateOrder(orderBean);
        }
        return null;
    }

    @GetMapping(value = "/getListOfAllOrders")
    List<OrderBean> getListOfAllOrders()throws FmkException{
        return  orderService.getListOfAllOrders();
    }

    @GetMapping(value = "/getOrderByOrderName/{idOrder}")
    OrderBean getOrderByOrderName(@PathVariable("idOrder") int idOrder)throws FmkException{
        return  orderService.getOrderById(idOrder);
    }

    @GetMapping(value = "/getOrderByIdCustomer/{idCustomer}")
    List<OrderBean> getOrderByIdCustomer(@PathVariable("idCustomer") int idCustomer)throws FmkException{
        return  orderService.getListOfAllOrdersByCustomerId(idCustomer);
    }

    @DeleteMapping(value = "/deleteOrderById/{id}")
    String deleteOrderById(@PathVariable("id") int id)throws FmkException{
        return orderService.deleteOrderById(id);
    }

    @GetMapping("/findByBookingDate")
    List<CustomerBean> findBookingDate(@RequestParam(required = false) String date) throws FmkException {
        LocalDate orderDate = LocalDate.parse(date);
        return  orderService.findBookingDate(orderDate);
    }

    @GetMapping("/getInvoiceDetails/{orderNo}")
    InvoiceBean getInvoiceDetails(@PathVariable("orderNo") String orderNo) throws FmkException {
        return orderService.getInvoiceDetails(orderNo);
    }

    @PostMapping(value = "/orderCompleted/{idOrder}")
    public OrderBean orderCompleted(@PathVariable("idOrder") int idOrder) throws FmkException{
        if (idOrder>0){
            return orderService.orderCompleted(idOrder);
        }
        return null;
    }
    @PostMapping(value = "/orderDelivered/{idOrder}")
    public OrderBean orderDelivered(@PathVariable("idOrder") int idOrder) throws FmkException{
        if (idOrder>0){
            return orderService.orderDelivered(idOrder);
        }
        return null;
    }

    @PostMapping(value = "/orderAlteration/{idOrder}/{alterComments}")
    public OrderBean orderAlteration(@PathVariable("idOrder") int idOrder, @PathVariable("alterComments") String alterComments) throws FmkException{
        if (idOrder>0 && alterComments!= null){
            return orderService.orderAlteration(idOrder,alterComments);
        }else {
            throw  new FmkException("OR1008","Please provide the required inputs");
        }
    }

    @PostMapping(value = "/schedulerForOrderDelivery")
    public void schedulerForOrderDelivery(){
        orderService.schedulerForOrderDelivery();
    }

}
