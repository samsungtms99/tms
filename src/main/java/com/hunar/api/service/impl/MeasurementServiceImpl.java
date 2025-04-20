package com.hunar.api.service.impl;

import com.hunar.api.bean.MeasurementBean;
import com.hunar.api.bean.TypeMeasurementBean;
import com.hunar.api.entity.Measurement;
import com.hunar.api.entity.TypeMeasurement;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.repository.MeasurementRepository;
import com.hunar.api.repository.TypeMeasurementRepository;
import com.hunar.api.service.MeasurementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    public static Logger logger = LogManager.getLogger(MeasurementServiceImpl.class);

    @Autowired
    TypeMeasurementRepository tmRepo;

    @Autowired
    MeasurementRepository measurementRepository;

    @Transactional(rollbackFor = {FmkException.class, Exception.class, SQLException.class})
    @Override
    public TypeMeasurementBean createMeasurement(TypeMeasurementBean measurementBean) throws FmkException {
        if (measurementBean!=null && measurementBean.getTypeName() !=null && measurementBean.getIdCustomer()>0){
            TypeMeasurement typeMeasurement = tmRepo.findByTypeNameAndIdCustomer(measurementBean.getTypeName(), measurementBean.getIdCustomer());
            if (typeMeasurement!=null){
                throw new FmkException("TM1001", "Measurement type "+ measurementBean.getTypeName() + " already exists");
            }

            TypeMeasurement typeMeasure = new TypeMeasurement();
           typeMeasure.setIdCustomer(measurementBean.getIdCustomer());
           typeMeasure.setTypeName(measurementBean.getTypeName());
           typeMeasure = tmRepo.save(typeMeasure);
           logger.info("measurement type saved successfully: "+typeMeasure.getIdTypeMeasure());
           if (typeMeasure.getIdTypeMeasure()>0){
               List<MeasurementBean> measurements = createMeasurements(measurementBean.getMeasurements(), typeMeasure.getIdTypeMeasure());
               TypeMeasurementBean typeMeasurementBean = new TypeMeasurementBean();
               BeanUtils.copyProperties(typeMeasure, typeMeasurementBean);
               typeMeasurementBean.setMeasurements(measurements);
               return  typeMeasurementBean;
           }

        }else {
            throw new FmkException("TM1002", "Invalid input values");
        }
        return null;
    }

    private List<MeasurementBean> createMeasurements(List<MeasurementBean> measurements, int idType) throws FmkException {
        if (measurements.isEmpty()){
            throw new FmkException("TM1003", "measurements are not present");
        }
//        List<Measurement> measurementList = measurementRepository.findAllByIdTypeMeasure(idType);
        for (MeasurementBean measurementBean : measurements){
            Measurement measurementExists = measurementRepository.findByFieldAndIdTypeMeasure(measurementBean.getField(),idType);
           if (measurementExists!=null){
               //update measurements
               measurementExists.setValue(measurementBean.getValue());
               measurementRepository.save(measurementExists);
               logger.info("updated measurment for: "+measurementExists.getField());
           }else {
               //create measurement
               Measurement measurement = new Measurement();
               measurement.setIdTypeMeasure(idType);
               measurement.setField(measurementBean.getField());
               measurement.setValue(measurementBean.getValue());
               measurementRepository.save(measurement);
               logger.info("measurement saved for field: "+measurement.getField()+" and value: "+measurement.getValue());
           }

        }
        return measurements;
    }

    @Override
    public TypeMeasurementBean updateMeasurment(TypeMeasurementBean measurementBean) throws FmkException {
       logger.info("update method calling: "+measurementBean.getTypeName());
        if (measurementBean!=null && measurementBean.getIdTypeMeasure() !=0 && measurementBean.getIdCustomer()>0){
            Optional<TypeMeasurement> typeMeasurement = tmRepo.findById(measurementBean.getIdTypeMeasure());
//                    findByTypeNameAndIdCustomer(measurementBean.getTypeName(), measurementBean.getIdCustomer());
            if (!typeMeasurement.isPresent()){
                throw new FmkException("TM1004", "Measurement type "+ measurementBean.getTypeName() + " not exists");
            }

            TypeMeasurement typeMeasure = typeMeasurement.get();
            typeMeasure.setIdCustomer(measurementBean.getIdCustomer());
            typeMeasure.setTypeName(measurementBean.getTypeName());
            typeMeasure = tmRepo.save(typeMeasure);
            logger.info("measurement type udated successfully: "+typeMeasure.getIdTypeMeasure());
            if (typeMeasure.getIdTypeMeasure()>0){
                List<MeasurementBean> measurements = createMeasurements(measurementBean.getMeasurements(), typeMeasure.getIdTypeMeasure());
                TypeMeasurementBean typeMeasurementBean = new TypeMeasurementBean();
                BeanUtils.copyProperties(typeMeasure, typeMeasurementBean);
                typeMeasurementBean.setMeasurements(measurements);
                return  typeMeasurementBean;
            }

        }else {
            throw new FmkException("TM1002", "Invalid input values");
        }
        return null;
    }

    @Override
    public List<TypeMeasurementBean> getAllMeasurements(int idCustomer) {
        List<TypeMeasurementBean> typeMeasurementBeanList = new ArrayList<>();
        if (idCustomer > 0){
            // fetch specific
            List<TypeMeasurement> typeMeasurementListEntity = tmRepo.findByIdCustomer(idCustomer);
            if (!typeMeasurementListEntity.isEmpty()){
                for (TypeMeasurement typeMeasurement : typeMeasurementListEntity){
                    TypeMeasurementBean typeMeasurementBean = new TypeMeasurementBean();
                    BeanUtils.copyProperties(typeMeasurement,typeMeasurementBean);
                    List<MeasurementBean> measurementBeanList =fetchMeasurementsByTypeId(typeMeasurement.getIdTypeMeasure());
                    typeMeasurementBean.setMeasurements(measurementBeanList);
                    typeMeasurementBeanList.add(typeMeasurementBean);
                }
                return typeMeasurementBeanList;
            }
        }else {
            // fetch All
            List<TypeMeasurement> typeMeasurementListEntity = tmRepo.findAll();
            if (!typeMeasurementListEntity.isEmpty()) {
                for (TypeMeasurement typeMeasurement : typeMeasurementListEntity) {
                    TypeMeasurementBean typeMeasurementBean = new TypeMeasurementBean();
                    BeanUtils.copyProperties(typeMeasurement, typeMeasurementBean);
                    List<MeasurementBean> measurementBeanList = fetchMeasurementsByTypeId(typeMeasurement.getIdTypeMeasure());
                    typeMeasurementBean.setMeasurements(measurementBeanList);
                    typeMeasurementBeanList.add(typeMeasurementBean);
                }
                return typeMeasurementBeanList;
            }

        }
        return null;
    }

    public List<MeasurementBean> fetchMeasurementsByTypeId(int idTypeMeasure) {
        List<Measurement> measurements = measurementRepository.findAllByIdTypeMeasure(idTypeMeasure);
        List<MeasurementBean> measurementBeans = null;
        if (!measurements.isEmpty()){
            measurementBeans = new ArrayList<>();
            for (Measurement measurement:measurements){
                MeasurementBean measurementBean = new MeasurementBean();
                BeanUtils.copyProperties(measurement,measurementBean);
                measurementBeans.add(measurementBean);
            }
            return  measurementBeans;
        }
        return new ArrayList<>();
    }

    @Override
    public String deleteMeasurementById(int id) {
        if (id>0){
            deleteMeasurementByIdType(id);
            tmRepo.deleteById(id);
        }else {
            return "Invalid Id: "+id;
        }
        return "Measurement deleted successfully..";
    }

    @Override
    public TypeMeasurementBean getMeasurementById(int idTypeMeasure) {
        if (idTypeMeasure>0){
            Optional<TypeMeasurement> typeMeasurement = tmRepo.findById(idTypeMeasure);
            if (typeMeasurement.isPresent()){
                TypeMeasurementBean typeMeasurementBean = new TypeMeasurementBean();
                BeanUtils.copyProperties(typeMeasurement.get(),typeMeasurementBean);
                typeMeasurementBean.setMeasurements(fetchMeasurementsByTypeId(typeMeasurement.get().getIdTypeMeasure()));
                return typeMeasurementBean;
            }
        }
        return  null;
    }

    private void deleteMeasurementByIdType(int id) {
        measurementRepository.deleteAllByIdTypeMeasure(id);
    }
}
