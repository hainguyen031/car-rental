package com.hainguyen.carrental.converter;

import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.model.Car;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class ListCarConverter implements Function<List<Car>, List<CarDTO>> {


    public List<CarDTO> apply(List<Car> carList) {
        List<CarDTO> carDTOList = new ArrayList<>();
        for (Car car : carList) {
            CarDTO carDTO = new CarDTO();
            BeanUtils.copyProperties(car, carDTO);
            carDTOList.add(carDTO);
        }
        return carDTOList;
    }
}
