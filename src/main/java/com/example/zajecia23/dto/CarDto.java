package com.example.zajecia23.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CarDto {

    private String mark;
    private String model;
    private LocalDate productionDate;
}
