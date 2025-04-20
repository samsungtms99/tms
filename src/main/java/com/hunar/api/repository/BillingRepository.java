package com.hunar.api.repository;

import com.hunar.api.entity.BillingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<BillingEntity, Integer> {
    boolean existsByIdOrderAndIdCustomer(int idOrder, int idCustomer);
}
