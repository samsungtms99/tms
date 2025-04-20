package com.hunar.api.repository;

import com.hunar.api.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findAllByIdCustomer(int custId);

    Order findByOrderNo(String orderNo);

    @Query("SELECT o FROM Order o WHERE o.deliveryDate = :targetDate")
    List<Order> findOrdersWithDeliveryDate(LocalDate targetDate);

    @Query("""
    SELECT o.orderNo
    FROM Order o
    WHERE o.orderNo LIKE CONCAT('ORD-', :date, '-%')
    ORDER BY CAST(SUBSTRING(o.orderNo, 14) AS int) DESC
    """)
    List<String> findLatestOrderNoByDate(@Param("date") String date, Pageable pageable);

    boolean existsByOrderNo(String newOrderId);

//    List<Order> findbyBookingDate(LocalDate date);
}
