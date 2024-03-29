package io.joyoungc.infrastructure.mongo;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/***
 * Created by Aiden Jeong on 2021.12.12
 */
@Getter
@ToString
@Document("customers")
public class Customer {

    @Id
    private String id;

    @Indexed(unique = true) // db.customers.createIndex({userId:1},{unique:true})
    private String userId;
    private String firstName;
    private String lastName;

    public Customer(String userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
