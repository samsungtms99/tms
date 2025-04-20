package com.hunar.api.bean;

import com.hunar.api.entity.CustomerEntity;
import com.hunar.api.entity.Measurement;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class TypeMeasurementBean {

    private int idTypeMeasure;
    private String typeName;
    private int idCustomer;
    private List<MeasurementBean> measurements = new ArrayList<>();

    public int getIdTypeMeasure() {
        return idTypeMeasure;
    }

    public void setIdTypeMeasure(int idTypeMeasure) {
        this.idTypeMeasure = idTypeMeasure;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public List<MeasurementBean> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementBean> measurements) {
        this.measurements = measurements;
    }
}
