package com.hunar.api.entity;

import com.hunar.api.generic.entity.GenericEntity;
import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "TM_MEASUREMENT")
public class Measurement extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_MEASUREMENT")
    private int idMeasure;

    @Column(name = "ID_TYPE_MEASURMENT")
    private int idTypeMeasure;

    @Column(name = "FIELD")
    private String field;

    @Column(name = "VALUE")
    private  String value;

//    @ManyToOne
//    @JoinColumn(name = "ID_TYPE_MEASURMENT")
//    private TypeMeasurement typeMeasurement;

    public int getIdMeasure() {
        return idMeasure;
    }

    public void setIdMeasure(int idMeasure) {
        this.idMeasure = idMeasure;
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

//    public TypeMeasurement getTypeMeasurement() {
//        return typeMeasurement;
//    }
//
//    public void setTypeMeasurement(TypeMeasurement typeMeasurement) {
//        this.typeMeasurement = typeMeasurement;
//    }

    public int getIdTypeMeasure() {
        return idTypeMeasure;
    }

    public void setIdTypeMeasure(int idTypeMeasure) {
        this.idTypeMeasure = idTypeMeasure;
    }
}
