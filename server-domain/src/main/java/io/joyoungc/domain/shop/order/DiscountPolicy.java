package io.joyoungc.domain.shop.order;


import io.joyoungc.domain.shop.member.Member;
import io.joyoungc.domain.shop.product.Product;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface DiscountPolicy {
    long getDiscountPrice(Member member, Product product);
}
