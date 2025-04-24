package com.hunar.api.repository;

import com.hunar.api.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageEntity,Integer> {
    Optional<ImageEntity> findByName(String fileName);

    List<ImageEntity> findAllByOrderId(int idOrder);
}
