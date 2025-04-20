package com.hunar.api.service;

import com.hunar.api.bean.MeasurementBean;
import com.hunar.api.bean.TypeMeasurementBean;
import com.hunar.api.exceptionHandling.util.FmkException;

import java.util.List;

public interface MeasurementService {
    public TypeMeasurementBean createMeasurement(TypeMeasurementBean measurementBean) throws FmkException;

    public TypeMeasurementBean updateMeasurment(TypeMeasurementBean measurementBean) throws FmkException;

    public List<TypeMeasurementBean> getAllMeasurements(int idCustomer);

    public String deleteMeasurementById(int id);

    TypeMeasurementBean getMeasurementById(int idTypeMeasure);
}
