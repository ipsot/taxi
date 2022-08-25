package ru.taxi.orderprocessor.api;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.taxi.orderprocessor.dto.CarCreatUpdateOperationDTO;
import ru.taxi.orderprocessor.dto.CarDTO;
import ru.taxi.orderprocessor.dto.FindCarsDto;
import ru.taxi.orderprocessor.logic.CarOperativeService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarOperationController {

    private final CarOperativeService carOperativeService;

    @PostMapping
    public CarDTO createCar(@Valid @RequestBody CarCreatUpdateOperationDTO carCreatUpdateOperationDTO) {
        log.debug("createCar .in - dto: {}", carCreatUpdateOperationDTO);
        var carEntity = carOperativeService.createOperation(carCreatUpdateOperationDTO);
        log.debug("createCar .out - response: {}", carEntity);
        return carEntity;
    }

    @PutMapping
    public CarDTO updateCar(@Valid @RequestBody CarCreatUpdateOperationDTO carDTO){
        log.debug("updateCar .in - dto: {}", carDTO);
        var carEntity = carOperativeService.update(carDTO);
        log.debug("updateCar .out - response: {}", carEntity);
        return carEntity;
    }
    @GetMapping("/{number}")
    public CarDTO findCarByNumber(@PathVariable String number){

        return carOperativeService.findByNumber(number);

    }

    @PostMapping("/find")
    public List<CarDTO> findCars(@Valid @RequestBody FindCarsDto findCarsDto){
        return carOperativeService.find(findCarsDto);

    }

    @GetMapping("/seecar")
    public String seeCar(){
        return "view/hello";
    }




}
