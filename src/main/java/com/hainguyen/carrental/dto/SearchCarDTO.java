package com.hainguyen.carrental.dto;

import com.hainguyen.carrental.util.Validation;
import lombok.Data;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Data
public class SearchCarDTO implements Validator {
    private int seat;
    private String carLocation;
    private LocalDate startDate;
    private LocalDate endDate;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchCarDTO searchCarDTO = (SearchCarDTO) target;

        LocalDate startDate = searchCarDTO.getStartDate();
        Validation.checkStartDate("startDate", startDate, errors);

        LocalDate endDate = searchCarDTO.getEndDate();
        Validation.checkEndDate("endDate",startDate, endDate, errors);

    }
}
