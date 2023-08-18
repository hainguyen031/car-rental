package com.hainguyen.carrental.dto;

import com.hainguyen.carrental.util.Validation;
import lombok.Data;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Data
public class BookingDTO implements Validator {
    private Long id;
    private Long carId;
    private Long customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String cccd;
    private String gplx;
    private String pickupLocation;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookingDTO bookingDTO = (BookingDTO) target;

        LocalDate startDate = bookingDTO.getStartDate();
        Validation.checkStartDate("startDate", startDate, errors);

        LocalDate endDate = bookingDTO.getEndDate();
        Validation.checkEndDate("endDate",startDate, endDate, errors);

        String cccd = bookingDTO.getCccd();
        Validation.checkCccd("cccd", cccd, errors);

        String gplx = bookingDTO.getGplx();
        Validation.checkGplx("gplx", gplx, errors);
    }
}
