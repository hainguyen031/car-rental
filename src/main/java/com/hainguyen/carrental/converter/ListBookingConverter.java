package com.hainguyen.carrental.converter;

import com.hainguyen.carrental.dto.BookingDTO;
import com.hainguyen.carrental.model.Booking;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class ListBookingConverter implements Function<List<Booking>, List<BookingDTO>> {

    public List<BookingDTO> apply(List<Booking> bookingList) {
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        for (Booking booking : bookingList) {
            BookingDTO bookingDTO = new BookingDTO();
            BeanUtils.copyProperties(booking, bookingDTO);
            bookingDTOList.add(bookingDTO);
        }
        return bookingDTOList;
    }
}
