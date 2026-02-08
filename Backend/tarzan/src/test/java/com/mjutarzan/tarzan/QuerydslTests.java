package com.mjutarzan.tarzan;

import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QuerydslTests {

    @Autowired
    EntityManager em;

    @Test
    public void startJpql(){

        Board selectBFromBoardB = em.createQuery("select b from Board b", Board.class).getSingleResult();
        System.out.println("selectBFromBoardB = " + selectBFromBoardB);

    }

    @Test
    public void startQuerydsl(){
//        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    }
}
