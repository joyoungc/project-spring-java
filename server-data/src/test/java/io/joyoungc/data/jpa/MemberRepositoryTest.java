package io.joyoungc.data.jpa;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.joyoungc.data.configuration.DataConfig;
import io.joyoungc.data.shop.domain.Grade;
import io.joyoungc.data.shop.domain.Member;
import io.joyoungc.data.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
//@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Repository.class}), showSql = false)
@DataJpaTest(showSql = false)
@Import(DataConfig.class)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
class MemberRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private MemberRepository memberRepository;



    @Test
    @DisplayName("QueryDsl 레포지토리 테스트")
    void testQueryDsl() {
        String testName = "테스트1";
        Member member = new Member(testName, Grade.BASIC);
        memberRepository.save(member);

        List<Member> members = memberRepository.findMembers(Grade.BASIC);
        assertThat(members).isNotEmpty();
    }



}