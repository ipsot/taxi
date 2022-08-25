package ru.taxi.orderprocessor.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.taxi.orderprocessor.entity.CarEntity;
import ru.taxi.orderprocessor.enums.PriorityClass;
import ru.taxi.orderprocessor.validation.ValidSortingField;

import javax.swing.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCarsDto {

    private ECriteria criteria;
    @Valid
    private Sort sort;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ECriteria{

        private CarEntity.CarClass carClass;
        private String color;
        private PriorityClass priorityClass;
        private LocalDate issuedAtGreater;
        private LocalDate issuedAtLower;
        private String model;


    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Sort{


        @ValidSortingField
        @NotBlank
        private String sortBy;

        @NotNull
        private SortOrder sortOrder;
    }
}
