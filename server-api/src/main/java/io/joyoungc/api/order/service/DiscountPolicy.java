package io.joyoungc.api.order.service;

import io.joyoungc.data.shop.domain.Member;
import io.joyoungc.data.shop.domain.Product;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface DiscountPolicy {
    long getDiscountPrice(Member member, Product product);
}
