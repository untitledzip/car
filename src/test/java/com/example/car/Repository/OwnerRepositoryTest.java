package com.example.car.Repository;

import com.example.car.Entity.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class OwnerRepositoryTest {

    @Autowired
    OwnerRepository ownerRepository;

    @Test
    @DisplayName("owner 테이블 저장 테스트")
    public void createOwner(){
        Owner owner = new Owner();
        owner.setFirstName("robert");
        owner.setLastName("jr");
        Owner savedOwner = ownerRepository.save(owner);

        System.out.println(owner.getFirstName() + owner.getLastName());
    }

}