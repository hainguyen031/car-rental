package com.hainguyen.carrental.converter;

import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.model.Car;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@AllArgsConstructor
public class CarConverter implements Function<Car, CarDTO> {
    public CarDTO apply(Car car) {
        CarDTO carDTO = new CarDTO();
        BeanUtils.copyProperties(car, carDTO);
        return carDTO;
    }
}
