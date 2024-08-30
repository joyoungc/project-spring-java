package io.joyoungc.domain.shop.order;


import io.joyoungc.domain.shop.member.Grade;
import io.joyoungc.domain.shop.member.Member;
import io.joyoungc.domain.shop.product.Product;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public class FixedDiscountPolicy implements DiscountPolicy {

    private final long FIXED_DISCOUNT_AMOUNT = 1000;

    @Override
    public long getDiscountPrice(Member member, Product product) {
        return member.getGrade() == Grade.VIP ? FIXED_DISCOUNT_AMOUNT : 0;
    }
}
