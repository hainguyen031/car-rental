package com.hainguyen.carrental.service;

import com.hainguyen.carrental.dto.BookingDTO;
import com.hainguyen.carrental.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookingService {
    List<BookingDTO> getBookingHistory(Long customerId);

    List<BookingDTO> getBookingHistoryForCar(Long carId);

    Page<BookingDTO> getBookingHistoryForCarPaging(Long id, Pageable pageable);
}
