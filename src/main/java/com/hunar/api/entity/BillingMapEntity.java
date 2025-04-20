package com.hunar.api.entity;

import com.hunar.api.generic.entity.GenericEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "TM_BILLING_MAP_FIELDS")
public class BillingMapEntity extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int idBillMap;

    @Column(name = "ID_BILLING")
    private int idBilling;

    @Column(name = "FIELD")
    private String field;

    @Column(name = "VALUE")
    private  String value;

    public int getIdBillMap() {
        return idBillMap;
    }

    public void setIdBillMap(int idBillMap) {
        this.idBillMap = idBillMap;
    }

    public int getIdBilling() {
        return idBilling;
    }

    public void setIdBilling(int idBilling) {
        this.idBilling = idBilling;
    }

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
