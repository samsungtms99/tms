package com.hunar.api.repository;

import com.hunar.api.entity.BillingMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BillingMapRepository extends JpaRepository<BillingMapEntity, Integer> {
    List<BillingMapEntity> findAllByIdBilling(int idBill);

    @Transactional
    @Modifying
    void deleteByIdBilling(int idBill);
}
