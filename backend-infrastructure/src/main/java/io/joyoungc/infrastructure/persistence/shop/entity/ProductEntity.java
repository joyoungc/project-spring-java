package io.joyoungc.infrastructure.persistence.shop.entity;

import io.joyoungc.infrastructure.persistence.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;

    public ProductEntity(String name, Long price) {
        this.name = name;
        this.price = price;
    }
}
