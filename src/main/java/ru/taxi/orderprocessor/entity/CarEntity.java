package ru.taxi.orderprocessor.entity;

import lombok.*;
import ru.taxi.orderprocessor.enums.PriorityClass;

import javax.persistence.*;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="cars")
public class CarEntity extends BaseEntity {

    private String model;

    @Column(unique = true)
    private String number;
    private String color;

    private LocalDate issuedAt;

    private CarClass carClass;

    @Enumerated(EnumType.STRING)
    private PriorityClass priorityClass;

    public enum CarClass{
        A,B,C,E,D,F
    }

}
