package ru.taxi.orderprocessor.logic;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.taxi.orderprocessor.dto.CarCreatUpdateOperationDTO;
import ru.taxi.orderprocessor.enums.PriorityClass;

import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarOperationsProcessor {

    @Value("${internal.cars.max-year}")
    private Integer maxYear;
    @Value("${internal.cars.offset}")
    private Integer offset;

    public PriorityClass doCalculateClass(CarCreatUpdateOperationDTO dto) {
        Period between1 = Period.between(LocalDate.now(), dto.getIssuedAt());
        var years = between1.getYears();

        if (years > maxYear) {
            throw new IllegalArgumentException("Exceeded allowed time period for car exploitation");
        }

        PriorityClass rawPriorityClass = PriorityClass.convert(dto.getCarClass());

        Integer downGradeToApply = doCalculateDowngrade(years);

        rawPriorityClass = downGradeToApply > rawPriorityClass.ordinal() ?
                PriorityClass.values()[0] :
                PriorityClass.values()[rawPriorityClass.ordinal() - downGradeToApply];

        return rawPriorityClass;
    }

    public Integer doCalculateDowngrade(Integer carAge) {

        int downgrade = carAge / offset;
        int tail = carAge % offset;
        if (tail == 0) {
            downgrade -= 1;
        }
        return downgrade;
    }
}
