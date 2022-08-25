package ru.taxi.orderprocessor.logic;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.taxi.orderprocessor.dao.CarOperationsRepository;
import ru.taxi.orderprocessor.dao.custom.CarOperationRepositoryCustom;
import ru.taxi.orderprocessor.dto.CarCreatUpdateOperationDTO;
import ru.taxi.orderprocessor.dto.CarDTO;
import ru.taxi.orderprocessor.dto.FindCarsDto;
import ru.taxi.orderprocessor.entity.CarEntity;
import ru.taxi.orderprocessor.enums.PriorityClass;
import ru.taxi.orderprocessor.mapper.CarMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarOperativeService {

    private final CarOperationsRepository repository;
    private final CarOperationsProcessor processor;
    private final CarOperationRepositoryCustom customRepository;
    private final CarMapper mapper;


    public CarDTO createOperation(CarCreatUpdateOperationDTO carCreatUpdateOperationDTO) {
        log.debug("create .in - dto {}", carCreatUpdateOperationDTO);
        CarEntity carEntity = mapper.dtoToEntity(carCreatUpdateOperationDTO);


        PriorityClass priorityClass = processor.doCalculateClass(carCreatUpdateOperationDTO);
        carEntity.setPriorityClass(priorityClass);
        log.info("create.in - calculated priority class: {}", priorityClass);


        var save = repository.save(carEntity);
        log.info("create.out - created car_entity with id {}", save.getId());
        CarDTO carDTO = mapper.entityToDto(save);
        log.debug("create .out - response{}", carDTO);
        return carDTO;

    }


    public CarDTO update(CarCreatUpdateOperationDTO carDTO) {

        log.debug("update .in - dto {}", carDTO);
        var stateNumber = carDTO.getNumber();

        var carEntity = findByNumberInternal(stateNumber);

        log.info("updated.in - found car record with number: {}", stateNumber);

        CarEntity entityUpdated = mapper.updateFromDto(carDTO, carEntity);

        PriorityClass priorityClass = processor.doCalculateClass(carDTO);
        entityUpdated.setPriorityClass(priorityClass);


        CarEntity updatedPersisted=repository.save(entityUpdated);
        log.info("updated.in - car record with number: {}", stateNumber);
        CarDTO updatedPersistedDto=mapper.entityToDto(updatedPersisted);
        log.debug("update .out - response{}", updatedPersistedDto);
        return updatedPersistedDto;

    }

    public List<CarDTO> find(FindCarsDto criteria){

        log.info("find.in - searching cars by criteria: {}", criteria);

       List<CarEntity> carEntities= customRepository.find(criteria.getCriteria(),criteria.getSort());
       var carDtos=mapper.entityToDto(carEntities);

        log.info("find.out - result: {}", carDtos);

       return carDtos;

    }

    public CarDTO findByNumber(String number){
        return mapper.entityToDto(findByNumberInternal(number));
    }

    public CarEntity findByNumberInternal(String number){
        var carEntity = repository.findByNumber(number).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Car with number %s not found in registry.", number));
        });
        return carEntity;
    }





}
