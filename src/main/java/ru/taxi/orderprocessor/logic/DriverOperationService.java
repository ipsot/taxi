package ru.taxi.orderprocessor.logic;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.taxi.orderprocessor.dao.DriverOperationsRepository;
import ru.taxi.orderprocessor.dto.CarDTO;
import ru.taxi.orderprocessor.dto.DriverDto;
import ru.taxi.orderprocessor.entity.DriverEntity;
import ru.taxi.orderprocessor.mapper.DriverMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverOperationService {

    private final DriverOperationsRepository driverOperationsRepository;
    private final CarOperativeService carOperativeService;
    private final DriverMapper driverMapper;

    public DriverEntity create(DriverDto dto) {

        DriverEntity map = driverMapper.map(dto);
        var byNumber = carOperativeService.findByNumberInternal(dto.getCarNumber());
        map.setCarEntity(byNumber);
        return driverOperationsRepository.save(map);

    }
}
