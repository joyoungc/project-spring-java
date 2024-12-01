package io.joyoungc.domain.model.order;


import io.joyoungc.domain.model.member.Member;
import io.joyoungc.domain.model.product.Product;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface DiscountPolicy {
    long getDiscountPrice(Member member, Product product);
}
