package io.joyoungc.data.mongodb;

import io.joyoungc.data.domain.mongo.Customer;
import io.joyoungc.data.domain.mongo.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/***
 * Created by Aiden Jeong on 2021.12.13
 *
 * 도커 local-mongodb 컨테이너 실행 후 진행
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MongoRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void mongoTest() {
        Customer customer1 = new Customer("1","Aiden", "Jeong");
        Customer customer2 = new Customer("2","Biden", "Young");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        List<Customer> all = customerRepository.findAll();
        System.out.println("all = " + all);
        List<Customer> list = customerRepository.findByFirstName("Aiden");
        assertThat(list).isNotEmpty().extracting(Customer::getFirstName).contains("Aiden");
    }
}
