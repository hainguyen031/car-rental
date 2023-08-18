package com.hainguyen.carrental.converter;

import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.model.Car;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class PageCarConverter implements Function<Page<Car>, Page<CarDTO>> {
    public Page<CarDTO> apply(Page<Car> carPage) {
        return carPage.map(car -> {
            CarDTO carDTO = new CarDTO();
            BeanUtils.copyProperties(car, carDTO);
            return carDTO;
        });
    }
}
