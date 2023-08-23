package com.hainguyen.carrental.repository;

import com.hainguyen.carrental.model.Booking;
import com.hainguyen.carrental.model.Car;
import com.hainguyen.carrental.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IBookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.car.id = :carId " +
            "AND ((b.startDate >= :rentalDate AND b.startDate <= :returnDate) OR " +
            "(b.endDate >= :rentalDate AND b.endDate <= :returnDate) OR " +
            "(b.startDate <= :rentalDate AND b.endDate >= :returnDate))")
    Long checkCarAvailability(@Param("carId") Long carId,
                                 @Param("rentalDate") LocalDate rentalDate,
                                 @Param("returnDate") LocalDate returnDate);

//    List<Booking> findAllByCustomerId(Long userID);

    List<Booking> findByUser(User user);
    Page<Booking> findByUser(User user, Pageable pageable);
    List<Booking> findByCar(Car car);
    Page<Booking> findByCar(Car car, Pageable pageable);
}
