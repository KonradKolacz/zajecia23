package com.example.zajecia23.service;

import com.example.zajecia23.domain.Car;
import com.example.zajecia23.exception.CarNotFoundException;
import com.example.zajecia23.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Car addCar(Car car){
        return carRepository.save(car);
    }

    public Car findById(Long id){
        return carRepository.findById(id).orElseThrow(CarNotFoundException::new);
    }

    public List<Car> findByMark(String mark){
        return carRepository.findByMark(mark);
    }

    public List<Car> findAll(){
        return carRepository.findAll();
    }

    public void deleteById(Long id){
        carRepository.deleteById(id);
    }

    public Car updateById(Long id, Car car){
        Car carForUpdate = carRepository.findById(id).orElseThrow(CarNotFoundException::new);
        carForUpdate.setHp(car.getHp());
        carForUpdate.setMark(car.getMark());
        carForUpdate.setModel(car.getModel());
        carForUpdate.setPrice(car.getPrice());
        carForUpdate.setProductionDate(car.getProductionDate());
        return carRepository.save(carForUpdate);
    }
}
