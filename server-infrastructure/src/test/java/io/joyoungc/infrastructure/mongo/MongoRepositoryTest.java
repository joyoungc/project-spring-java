package io.joyoungc.infrastructure.mongo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/***
 * Created by Aiden Jeong on 2021.12.13
 *
 * 도커 local-mongodb 컨테이너 실행 후 진행
 */
@SpringBootTest
@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "mongo")
class MongoRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void mongo_save_and_findAll() {
        Customer customer1 = new Customer("1","Aiden", "Jeong");
        Customer customer2 = new Customer("2","Biden", "Young");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        List<Customer> all = customerRepository.findAll();
        System.out.println("all = " + all);
        List<Customer> list = customerRepository.findByFirstName("Aiden");
        Assertions.assertThat(list).isNotEmpty().extracting(Customer::getFirstName).contains("Aiden");
    }
}
