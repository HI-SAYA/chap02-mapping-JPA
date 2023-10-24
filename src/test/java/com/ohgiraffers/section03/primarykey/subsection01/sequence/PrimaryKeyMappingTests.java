package com.ohgiraffers.section03.primarykey.subsection01.sequence;

import org.junit.jupiter.api.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class PrimaryKeyMappingTests {

    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeAll // 모든 테스트 수행하기 전에 딱 한번
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach //  테스트가 수행 되기 전마다 한번씩
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll // 모든 테스트 수행하기 전에 딱 한번
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach //  테스트가 수행 되기 전마다 한번씩
    public void closeManager() {
        entityManager.close();
    }

    @Test
    public void 식별자_매핑_테스트() {
        //given
        Member member = new Member();
        // member.setMemberNo(1); 시퀀스 설정했기 때문에 제거한다.
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");
        member.setPhone("010-1234-5678");
        member.setAddress("서울시 종로구");
        member.setEnrollDate(new Date());
        member.setMemberRole("ROLE_MEMBER");
        member.setStatus("Y");
        Member member2 = new Member();
        member2.setMemberId("user02");
        member2.setMemberPwd("pass02");
        member2.setNickname("유관순");
        member2.setPhone("010-2234-7789");
        member2.setAddress("서울시 동작구");
        member2.setEnrollDate(new Date());
        member2.setMemberRole("ROLE_MEMBER");
        member2.setStatus("Y");
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(member);
        entityManager.persist(member2);
        entityTransaction.commit();
        //then
        String jpql = "SELECT A.memberNo FROM member_section03_subsection01 A";
        // 테이블 명이 아니라 엔티티 명을 사용한다.
        // 별칭(A)을 반드시 사용해야 한다. (A.memberNo)
        // select절에 필드명을 사용한다. (memberNo)
        List<Integer> memberNoList = entityManager.createQuery(jpql, Integer.class).getResultList();
        // 반환받는 값이 여러개면 getResultList();
        // 반환받는 값이 하나면 getSingleResult();
        memberNoList.forEach(System.out::println);

    }
}
