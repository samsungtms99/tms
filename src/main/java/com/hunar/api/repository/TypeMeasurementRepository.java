package com.hunar.api.repository;

import com.hunar.api.entity.TypeMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeMeasurementRepository extends JpaRepository<TypeMeasurement, Integer> {
    TypeMeasurement findByTypeName(String typeName);

    List<TypeMeasurement> findByIdCustomer(int idCustomer);

    TypeMeasurement findByTypeNameAndIdCustomer(String typeName, int idCustomer);
}
