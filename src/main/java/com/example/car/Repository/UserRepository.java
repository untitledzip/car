package com.example.car.Repository;

import com.example.car.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long> {

    //@RepositoryRestResource(exported = false)
    // - 리포지터리가 REST 리소스로 노출되지 않음

    Optional<User> findByUserName(String userName);
}
