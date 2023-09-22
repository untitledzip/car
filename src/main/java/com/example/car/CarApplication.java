package com.example.car;

import com.example.car.Entity.Car;
import com.example.car.Entity.Owner;
import com.example.car.Repository.CarRepository;
import com.example.car.Repository.OwnerRepository;
import com.example.car.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarApplication implements CommandLineRunner{
	/* 
		CommandLineRunner
	 	 - 애플리케이션이 완전히 시작되기 전에 추가 코드를 실행할 수 있어 예제 데이터를 준비하기에 적합
	*/

	private static final Logger logger = LoggerFactory.getLogger(CarApplication.class);

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CarApplication.class, args);
	}

	// implements CommandLineRunner
	@Override
	public void run(String... args) throws Exception {
		carRepository.save(new Car("Ford", "Mustang", "Red", "ADF-1121", 2021, 59000, "1"));
		carRepository.save(new Car("Niro", "Kia", "Black", "KIA-2023", 2022, 35000,"2"));
		carRepository.save(new Car("Toyota", "Prius", "Silver", "KKO-0212", 2020, 30000,"3"));

		//등록된 모든 자동차의 정보를 콘솔에 로깅
		for(Car car : carRepository.findAll()){
			logger.info(car.getBrand() + " " + car.getModel());
		}

		ownerRepository.save(new Owner("kim", "a"));
		ownerRepository.save(new Owner("lee", "b"));
		ownerRepository.save(new Owner("park", "c"));

		//등록된 모든 자동차의 정보를 콘솔에 로깅
		for(Owner owner : ownerRepository.findAll()){
			logger.info(owner.getFirstName() + " " + owner.getLastName());
		}
	}

//	@Override
//	public void run(String... args) throws Exception {
//		ownerRepository.save(new Owner("kim", "a"));
//		ownerRepository.save(new Owner("lee", "b"));
//		ownerRepository.save(new Owner("park", "c"));
//
//		//등록된 모든 자동차의 정보를 콘솔에 로깅
//		for(Owner owner : ownerRepository.findAll()){
//			logger.info(owner.getFirstName() + " " + owner.getLastName());
//		}
//	}

}
