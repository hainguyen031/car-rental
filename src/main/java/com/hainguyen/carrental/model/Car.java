package com.hainguyen.carrental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "seat")
    private int seat;

    @Column(name = "rental_price")
    private int rentPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "url2")
    private String url2;

    @Column(name = "url3")
    private String url3;

    @Column(name = "url4")
    private String url4;

    @Column(name = "car_location")
    private String carLocation;

    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<Booking> bookingList;
}
