package com.example.car.Controller;

import com.example.car.Entity.Car;
import com.example.car.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @RequestMapping("/cars")
    public Iterable<Car> getCars(){
        //자동차를 검색하고 반환
        return carRepository.findAll();
    }
}
