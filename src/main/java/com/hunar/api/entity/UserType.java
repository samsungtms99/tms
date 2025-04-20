package com.hunar.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TM_USER_TYPE")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_TYPE")
    private int IdType;

    @Column(name = "TYPE_NAME", unique = true)
    private String typeName;

    public UserType(){
        super();
    }

    public UserType(int idType, String typeName) {
        IdType = idType;
        this.typeName = typeName;
    }

    public int getIdType() {
        return IdType;
    }

    public void setIdType(int idType) {
        IdType = idType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
