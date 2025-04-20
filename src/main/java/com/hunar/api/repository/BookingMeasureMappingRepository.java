package com.hunar.api.repository;

import com.hunar.api.entity.BookingMeasuremntMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookingMeasureMappingRepository extends JpaRepository<BookingMeasuremntMapping, Integer> {

    @Transactional
    @Modifying
    void deleteAllByIdOrder(int orderId);

    List<BookingMeasuremntMapping> findAllByIdOrder(int orderId);
}
