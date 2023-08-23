package com.hainguyen.carrental.dto;

import com.hainguyen.carrental.model.Car;
import com.hainguyen.carrental.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDTOResponse {
    private Long id;
    private Long carId;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String cccd;
    private String gplx;
    private String pickupLocation;
}
