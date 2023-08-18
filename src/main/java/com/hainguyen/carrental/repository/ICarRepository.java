package com.hainguyen.carrental.repository;

import com.hainguyen.carrental.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ICarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByStatus(boolean status);

    List<Car> findAllByStatusOrderByRentPriceAsc(boolean status); // Sắp xếp tăng dần theo giá

    List<Car> findAllByStatusOrderByRentPriceDesc(boolean status); // Sắp xếp giảm dần theo giá

    @Query(value = "SELECT c\n" +
            "FROM Car c\n" +
            "WHERE c.seat = ?1 AND\n" +
            "c.carLocation = ?2 AND\n" +
            "c.status = true AND\n" +
            "NOT EXISTS (\n" +
            "    SELECT b.car.id\n" +
            "    FROM Booking b\n" +
            "    WHERE b.car.id = c.id AND\n" +
            "    (b.startDate >= ?3 AND b.startDate <= ?4 OR\n" +
            "    b.endDate >= ?3 AND b.endDate <= ?4 OR\n" +
            "    b.startDate <= ?3 AND b.endDate >= ?4)\n" +
            ")"
            , nativeQuery = false)
    List<Car> findAllBySeatAndCarLocationAndRentalDateAndReturnDate(int seat, String carLocation, LocalDate rentalDate, LocalDate returnDate);

    Page<Car> findAllByStatus(boolean b, Pageable pageable);
}
