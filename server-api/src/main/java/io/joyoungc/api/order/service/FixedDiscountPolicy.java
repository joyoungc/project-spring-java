package io.joyoungc.api.order.service;

import io.joyoungc.data.shop.domain.Grade;
import io.joyoungc.data.shop.domain.Member;
import io.joyoungc.data.shop.domain.Product;
import org.springframework.stereotype.Component;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
@Component
public class FixedDiscountPolicy implements DiscountPolicy {

    private final long fixedDiscountAmount = 1000;

    @Override
    public long getDiscountPrice(Member member, Product product) {
        return member.getGrade() == Grade.VIP ? fixedDiscountAmount : 0;
    }
}
