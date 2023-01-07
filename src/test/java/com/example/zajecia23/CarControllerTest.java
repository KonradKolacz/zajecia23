package com.example.zajecia23;

import com.example.zajecia23.command.CarCommand;
import com.example.zajecia23.domain.Car;
import com.example.zajecia23.exception.CarNotFoundException;
import com.example.zajecia23.repository.CarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class CarControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void shouldAddCar() throws Exception {
        CarCommand carCommand = new CarCommand("ferrari", "488", LocalDate.now(), 400, 400);

        this.mockMvc.perform(post("/app")
                        .content(objectMapper.writeValueAsString(carCommand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mark").value("ferrari"));
    }

    @Test
    public void shouldGetOneCar() throws Exception {
        Car car = new Car(1L, "ferrari", "488", LocalDate.now(), 400, 400);
        carRepository.save(car);
        this.mockMvc.perform(get("/app/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("488"));
    }

    @Test
    public void shouldThrowExceptionWhenCarNotExists() throws Exception {
        Car car = new Car(1L, "ferrari", "488", LocalDate.now(), 400, 400);
        carRepository.save(car);
        this.mockMvc.perform(get("/app/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CarNotFoundException));
    }

    @Test
    public void shouldGetAllCars() throws Exception {
        Car car1 = new Car(1L, "ferrari", "488", LocalDate.now(), 400, 400);
        Car car2 = new Car(2L, "bmw", "x6", LocalDate.now(), 450, 500);
        carRepository.save(car1);
        carRepository.save(car2);

        this.mockMvc.perform(get("/app"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void shouldDeleteCar() throws Exception {
        Car car1 = new Car(1L, "ferrari", "488", LocalDate.now(), 400, 400);
        carRepository.save(car1);

        this.mockMvc.perform(delete("/app/1"))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/app"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldUpdateCar() throws Exception {
        Car car1 = new Car(1L, "ferrari", "488", LocalDate.now(), 400, 400);
        carRepository.save(car1);
        CarCommand carCommand = new CarCommand("bmw", "x6", LocalDate.now(), 500, 700);


        this.mockMvc.perform(put("/app/1")
                        .content(objectMapper.writeValueAsString(carCommand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mark").value("bmw"));
    }
}
