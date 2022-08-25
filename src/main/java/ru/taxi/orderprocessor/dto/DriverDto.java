package ru.taxi.orderprocessor.dto;

import liquibase.pro.packaged.A;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.taxi.orderprocessor.entity.CarEntity;
import ru.taxi.orderprocessor.entity.DriverEntity;

import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDto {

    private String firstName;
    private String secondName;

    private String licenseNumber;
    private Integer drivingExperience;


    private DriverEntity.DriverStatus status;

//    @OneToMany
//    private List<CarEntity> carEntity;

    private boolean terminated;
    private String carNumber;
}
