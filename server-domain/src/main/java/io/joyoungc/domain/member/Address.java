package io.joyoungc.domain.member;

import lombok.Getter;

/***
 * Created by Aiden Jeong on 2021.12.13
 */
@Getter
public class Address {

    private String city;
    private String street;
    private String zipCode;

    protected Address() {
    }

    public Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

}
