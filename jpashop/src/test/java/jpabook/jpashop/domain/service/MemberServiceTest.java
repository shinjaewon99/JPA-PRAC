package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.entity.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/*
@Rollback(value = false)
@Transactional이 있으면 기본적으로 Rollback을 함으로 insert문이 로직에서 안보이는데
@Rollback (value = false)를 하게되면 로직에서 insert문이 보이게된다.
*/
@SpringBootTest
@Transactional
class MemberServiceTest {


    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    void 회원가입() {

        //given
        Member member = new Member();
        member.setName("shin");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush(); // 영속성 컨텍스트에 있는 변경이나 등록내용을 데이터베이스에 반영.
        assertEquals(member, memberRepository.findOne(savedId));


    }


    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("shin1");

        Member member2 = new Member();
        member2.setName("shin1");

        //when
        memberService.join(member1);

        //then
        IllegalStateException thrown = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));// 예외가 발생해야한다.
        assertEquals("이미 존재하는 회원입니다", thrown.getMessage());

    }
}