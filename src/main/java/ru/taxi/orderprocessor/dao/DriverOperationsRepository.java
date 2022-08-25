package ru.taxi.orderprocessor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.taxi.orderprocessor.entity.DriverEntity;
import ru.taxi.orderprocessor.logic.DriverOperationService;

import java.util.UUID;

@Repository
public interface DriverOperationsRepository extends JpaRepository<DriverEntity, UUID> {





}
