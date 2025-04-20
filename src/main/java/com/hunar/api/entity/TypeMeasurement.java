package com.hunar.api.entity;

import com.hunar.api.generic.entity.GenericEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TM_TYPE_MEASUREMENT")
public class TypeMeasurement extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_TYPE_MEASURMENT")
    private int idTypeMeasure;

    @Column(name = "TYPE_NAME")
    private String typeName;

    @Column(name = "ID_CUSTOMER")
    private int idCustomer;

//    @ManyToOne
////    @JoinColumn(name = "ID_CUSTOMER")
//    private CustomerEntity customer;

//    @OneToMany(mappedBy = "typeMeasurement", cascade = CascadeType.ALL)
//    private List<Measurement> measurements = new ArrayList<>();

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

//    public CustomerEntity getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(CustomerEntity customer) {
//        this.customer = customer;
//    }
//
//    public List<Measurement> getMeasurements() {
//        return measurements;
//    }
//
//    public void setMeasurements(List<Measurement> measurements) {
//        this.measurements = measurements;
//    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }
}
