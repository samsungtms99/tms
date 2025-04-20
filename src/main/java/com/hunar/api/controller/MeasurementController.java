package com.hunar.api.controller;

import com.hunar.api.bean.MeasurementBean;
import com.hunar.api.bean.TypeMeasurementBean;
import com.hunar.api.constant.Constants;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = Constants.CROSS_ORIGIN)
@RequestMapping("/measurements")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping(value = "createMeasurment")
    public TypeMeasurementBean createMeasurment(@RequestBody TypeMeasurementBean measurementBean) throws FmkException {
        return measurementService.createMeasurement(measurementBean);
    }

    @PostMapping(value = "updateMeasurment")
    public TypeMeasurementBean updateMeasurment(@RequestBody TypeMeasurementBean measurementBean) throws FmkException {
        return measurementService.updateMeasurment(measurementBean);
    }

    @GetMapping(value = "/getAllMeasurements")
    public List<TypeMeasurementBean> getAllMeasurements(@RequestParam(required = false) int idCustomer){
        return  measurementService.getAllMeasurements(idCustomer);
    }

    @GetMapping(value = "/getMeasurementById/{idTypeMeasure}")
    public TypeMeasurementBean getMeasurementById(@PathVariable("idTypeMeasure") int idTypeMeasure){
        return  measurementService.getMeasurementById(idTypeMeasure);
    }

    @DeleteMapping(value = "/deleteMeasurementById/{id}")
    public String deleteMeasurementById(@PathVariable("id") int id){
        return  measurementService.deleteMeasurementById(id);
    }
}
