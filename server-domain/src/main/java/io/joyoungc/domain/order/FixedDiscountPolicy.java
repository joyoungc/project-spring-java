package io.joyoungc.domain.order;


import io.joyoungc.domain.member.Grade;
import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.product.Product;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public class FixedDiscountPolicy implements DiscountPolicy {

    private final long fixedDiscountAmount = 1000;

    @Override
    public long getDiscountPrice(Member member, Product product) {
        return member.getGrade() == Grade.VIP ? fixedDiscountAmount : 0;
    }
}
