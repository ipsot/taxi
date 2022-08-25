package ru.taxi.orderprocessor.dto;


import lombok.*;
import ru.taxi.orderprocessor.entity.CarEntity;
import ru.taxi.orderprocessor.enums.PriorityClass;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarCreatUpdateOperationDTO {

    @NotBlank
    private String model;

    @Pattern(regexp = "^[АВЕКМНОРСТУХ]\\d{3}(?<!000)[АВЕКМНОРСТУХ]{2}\\d{2,3}$",message = ""+ "Должно соответстовать формату гос.знака РФ. А111АА11")
    @NotBlank
    private String number;
    @NotBlank
    private String color;

    @NotNull
    private LocalDate issuedAt;

    @NotNull
    private CarEntity.CarClass carClass;

}
