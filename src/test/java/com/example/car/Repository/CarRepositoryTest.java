package com.example.car.Repository;

import com.example.car.Entity.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.swing.plaf.PanelUI;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;

    @Test
    @DisplayName("자동차 테이블 테스트")
    public void createCarTest(){
        Car car = new Car();
        car.setBrand("테스트 브랜드 1");
        car.setColor("테스트 색상");
        car.setModel("테스트 모델");
        car.setRegisterNumber("20230922-test");
        car.setPrice(10000);
        car.setYear(2023);
        car.setDescription("테스트 자동차 상세 설명");
        Car savedCar = carRepository.save(car);
    }

    public void createCarList(){
        for(int i=1; i<=10; i++) {
            Car car = new Car();
            car.setBrand("테스트 브랜드" + i);
            car.setColor("테스트 색상" + i);
            car.setModel("테스트 모델" + i);
            car.setRegisterNumber("20230922-test" + i);
            car.setPrice(10000 + i);
            car.setYear(2023 + i);
            car.setDescription("테스트 자동차 상세 설명" + i);
            Car savedCar = carRepository.save(car);
        }
    }

    @Test
    @DisplayName("브랜드로 자동차를 검색")
    public void findByBrandTest(){
        this.createCarList();
        List<Car> carList = carRepository.findByBrand("테스트 브랜드5");
        for(Car car : carList){
            System.out.println("findByBrand: " + car.getBrand());
        }
    }

    @Test
    @DisplayName("색상으로 자동차 검색")
    public void findByColorTest(){
        this.createCarList();
        List<Car> carList = carRepository.findByColor("테스트 색상3");
        for(Car car : carList){
            System.out.println("findByColor: " + car.getColor());
        }
    }

    @Test
    @DisplayName("연도로 자동차 검색")
    public void findByYearTest(){
        this.createCarList();
        List<Car> carList = carRepository.findByYear(2025);
        for(Car car : carList){
            System.out.println("findByYear: " + car.getYear());
        }
    }

    @Test
    @DisplayName("브랜드와 모델로 자동차 검색")
    public void findByBrandAndModelTest(){
        this.createCarList();
        List<Car> carList = carRepository.findByBrandAndModel("테스트 브랜드3", "테스트 모델3");
        for(Car car : carList){
            System.out.println("brand: " + car.getBrand());
            System.out.println("model: " + car.getModel());
        }
    }

    @Test
    @DisplayName("브랜드나 색상으로 자동차 검색")
    public void findByBrandOrColorTest(){
        this.createCarList();
        List<Car> carList = carRepository.findByBrandOrColor("테스트 브랜드4", "테스트 색상7");
        for(Car car : carList){
            System.out.println("brand: " + car.getBrand());
            System.out.println("color: " + car.getColor());
        }
    }

    @Test
    @DisplayName("브랜드로 자동차를 검색하고 연도로 정렬")
    public void findByBrandOrderByYearAsc(){
        this.createCarList();
        List<Car> carList = carRepository.findByBrandOrderByYearAsc("테스트 브랜드5");

        for(Car car : carList){
            System.out.println(car.getYear());
        }
    }

//    @Test
//    @DisplayName("SQL 문을 이용해 브랜드로 자동차를 검색(select c from Car c where c.brand = ?1)")
//    public void findByBrandTest(){
//        this.createCarList();
//        List<Car> carList = carRepository.findByBrand("테스트 브랜드9");
//        for(Car car : carList){
//            System.out.println("brand: " + car.getBrand());
//        }
//    }

    @Test
    @DisplayName("SQL 문을 이용해 브랜드로 자동차를 검색(select c from Car c where c.brand like %?1)")
    public void findByBrandEndsWithTest(){
        this.createCarList();
        List<Car> carList = carRepository.findByBrandEndsWith("테스트 브랜드10");
        for(Car car : carList){
            System.out.println("brand: " + car.getBrand());
        }
    }
}