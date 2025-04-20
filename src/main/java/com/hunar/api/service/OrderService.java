package com.hunar.api.service;

import com.hunar.api.bean.CustomerBean;
import com.hunar.api.bean.InvoiceBean;
import com.hunar.api.bean.OrderBean;
import com.hunar.api.exceptionHandling.util.FmkException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderBean createOrder(OrderBean orderBean) throws FmkException, IOException;
    OrderBean updateOrder(OrderBean orderBean) throws FmkException;
    List<OrderBean> getListOfAllOrders()throws FmkException;
    OrderBean getOrderById(int idOrder)throws FmkException;
    String deleteOrderById(int idOrder ) throws  FmkException;
    List<CustomerBean> findBookingDate(LocalDate date) throws FmkException;
    List<OrderBean> getListOfAllOrdersByCustomerId(int custId)throws FmkException;
    InvoiceBean getInvoiceDetails(String orderNo);
    OrderBean orderDelivered(int idCustomer);
    OrderBean orderCompleted(int idCustomer);

    OrderBean orderAlteration(int idOrder, String alterComments);

    void schedulerForOrderDelivery();
}
