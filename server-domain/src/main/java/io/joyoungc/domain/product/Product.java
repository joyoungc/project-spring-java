package io.joyoungc.domain.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {

    private Long id;
    private String name;
    private Long price;

    public Product(String name, Long price) {
        this.name = name;
        this.price = price;
    }
}
