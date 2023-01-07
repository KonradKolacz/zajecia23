package com.example.zajecia23.controller;

import com.example.zajecia23.command.CarCommand;
import com.example.zajecia23.domain.Car;
import com.example.zajecia23.dto.CarDto;
import com.example.zajecia23.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final ModelMapper modelMapper;


//    @PostMapping
//    public CarDto save(@RequestBody CarCommand carCommand) {
//        Car car = modelMapper.map(carCommand, Car.class);
//        Car carSaved = carService.addCar(car);
//        return modelMapper.map(carSaved, CarDto.class);
//    }

    @PostMapping
    public ResponseEntity<CarDto> add(@RequestBody CarCommand carCommand) {
        return new ResponseEntity<>(modelMapper.map(carService.addCar(modelMapper.map(carCommand, Car.class)),
                CarDto.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(modelMapper.map(carService.findById(id), CarDto.class), HttpStatus.OK);
    }

    @GetMapping("/mark/{mark}")
    public ResponseEntity<List<CarDto>> findByMark(@PathVariable("mark") String mark){
        return new ResponseEntity<>(carService.findAll().stream().map(car->modelMapper.map(car, CarDto.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> findAll(){
        return new ResponseEntity<>(carService.findAll().stream().map(car->modelMapper.map(car, CarDto.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarDto> deleteById(@PathVariable("id") Long id){
        carService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateById(@PathVariable("id") Long id, @RequestBody CarCommand car){
        return new ResponseEntity<>(modelMapper.map(carService.updateById(id, modelMapper.map(car, Car.class)), CarDto.class), HttpStatus.OK);
    }

    //delete by id
    //update by id
    //find by name
    // + poczytac o @Bean, @Configuration, @PathVariable, @RequestParam, @RequestBody


}
