package io.joyoungc.domain.model.order;


import io.joyoungc.domain.model.member.Grade;
import io.joyoungc.domain.model.member.Member;
import io.joyoungc.domain.model.product.Product;

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
