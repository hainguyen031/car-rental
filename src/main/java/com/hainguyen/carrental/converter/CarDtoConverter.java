package com.hainguyen.carrental.converter;

import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.model.Car;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@AllArgsConstructor
public class CarDtoConverter implements Function<CarDTO, Car> {
    public Car apply(CarDTO carDTO) {
        Car car = new Car();
        BeanUtils.copyProperties(carDTO, car);
        return car;
    }
}
