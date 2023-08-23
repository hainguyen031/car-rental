package com.hainguyen.carrental.service;

import com.hainguyen.carrental.dto.BookingDTO;
import com.hainguyen.carrental.dto.BookingDTORequest;
import com.hainguyen.carrental.dto.BookingDTOResponse;
import com.hainguyen.carrental.model.Booking;
import com.hainguyen.carrental.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookingService {
    List<BookingDTOResponse> getBookingHistory(Long customerId);

    List<BookingDTOResponse> getBookingHistoryForCar(Long carId);

    Page<BookingDTOResponse> getBookingHistoryForCarPaging(Long id, Pageable pageable);

    List<BookingDTOResponse> findAll();

    boolean saveBooking(BookingDTORequest bookingDTORequest);

    Page<BookingDTOResponse> findAllBookingByUser(User user, Pageable pageable);

}
