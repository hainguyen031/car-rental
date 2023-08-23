package com.hainguyen.carrental.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDTORequest {
    private Long id;
    private Long carId;
    private String cusName;
//    private String startDateStr;
//    private String endDateStr;
    private LocalDate startDate;
    private LocalDate endDate;
    private String cccd;
    private String gplx;
    private String pickupLocation;
}
