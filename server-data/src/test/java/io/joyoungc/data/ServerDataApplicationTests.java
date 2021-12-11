package io.joyoungc.data;

import io.joyoungc.data.domain.mongo.Customer;
import io.joyoungc.data.domain.mongo.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ServerDataApplicationTests {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    Environment env;

    @Test
    void envTest() {
        assertThat(env.getProperty("spring.data.mongodb.auto-index-creation")).isEqualTo("true");
    }

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
        // customerRepository.deleteAll();
    }

}
