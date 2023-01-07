package com.example.zajecia23.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class CarCommand {

    private String mark;
    private String model;
    private LocalDate productionDate;
    private int hp;
    private double price;

}
