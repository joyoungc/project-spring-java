package io.joyoungc.domain.order;


import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.product.Product;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface DiscountPolicy {
    long getDiscountPrice(Member member, Product product);
}
