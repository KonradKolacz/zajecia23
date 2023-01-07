package com.example.zajecia23.repository;

import com.example.zajecia23.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByMark(String mark);
}
