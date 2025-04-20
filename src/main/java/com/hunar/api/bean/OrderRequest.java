package com.hunar.api.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    private int orderId;

    private String orderNo;

    private LocalDate bookingDate;

    private LocalDate deliveryDate;

    private String comments;

    private String type; // pant, shirt etc

    private double amount;

    private double totalAmt;

    private int quantity;

    private int idCustomer;

    private  String orderStatus;

    private List<Integer> idTypeMeasures = new ArrayList<>();

    private List<TypeMeasurementBean> typeMeasurementBeans = new ArrayList<>();
}
