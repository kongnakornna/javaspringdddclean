package com.icmon.module.customer.infrastructure.repository;

import com.icmon.module.customer.infrastructure.entity.CarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID> {
    Optional<CarEntity> findByLicensePlate(String licensePlate);
    List<CarEntity> findByCustomerIdAndDeletedFalse(UUID customerId);
    boolean existsByLicensePlate(String licensePlate);

    @Query("SELECT c FROM CarEntity c WHERE c.deleted = false " +
           "AND (:brand IS NULL OR c.brand = :brand) " +
           "AND (:model IS NULL OR LOWER(c.model) LIKE LOWER(CONCAT('%', :model, '%'))) " +
           "AND (:year IS NULL OR c.year = :year)")
    Page<CarEntity> searchCars(
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("year") Integer year,
            Pageable pageable);
}
