package io.joyoungc.data.domain.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/***
 * Created by Aiden Jeong on 2021.12.12
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    List<Customer> findByFirstName(String firstName);
}
