package com.hunar.api.repository;

import com.hunar.api.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findAllByIdTypeMeasure(int idTypeMeasure);

    Measurement findByField(String field);

    Measurement findByFieldAndIdTypeMeasure(String field, int idType);

    @Transactional
    @Modifying
    void deleteAllByIdTypeMeasure(int id);
}
