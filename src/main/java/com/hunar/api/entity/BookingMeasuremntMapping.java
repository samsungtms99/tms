package com.hunar.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TM_BOOKING_MEASURE_MAPPING")
public class BookingMeasuremntMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idMapping;
    private int idOrder;
    private int idTypeMeasurement;

    public int getIdMapping() {
        return idMapping;
    }

    public void setIdMapping(int idMapping) {
        this.idMapping = idMapping;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdTypeMeasurement() {
        return idTypeMeasurement;
    }

    public void setIdTypeMeasurement(int idTypeMeasurement) {
        this.idTypeMeasurement = idTypeMeasurement;
    }
}
