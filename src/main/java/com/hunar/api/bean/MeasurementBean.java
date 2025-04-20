package com.hunar.api.bean;

import jakarta.persistence.Column;

public class MeasurementBean {

    private String field;
    private  String value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
