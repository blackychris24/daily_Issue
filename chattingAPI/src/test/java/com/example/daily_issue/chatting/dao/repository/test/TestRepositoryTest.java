package com.example.daily_issue.chatting.dao.repository.test;

import com.example.daily_issue.chatting.domain.test.A;
import com.example.daily_issue.chatting.domain.test.B;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-12
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-12)
 */

/**
 *
 *
 */
@DataJpaTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestRepositoryTest {

    @Autowired
    TestARepository testARepository;
    @Autowired
    TestBRepository testBRepository;

    @Test
    @Order(1)
    @Rollback(false)
    public void dataSaveTest()
    {
        List<A> listA = createA(10);
        listA.forEach(a -> {
            a.getListB().addAll(createB(10));
            testARepository.save(a);
        });

        /*A a = new A();
        a.setName("A1");
        a.getListB().addAll(createB(10));
        a = testARepository.save(a);
        testARepository.flush();*/


        System.out.println("===========================");


    }

    @Test
    @Order(2)
    @Rollback(false)
    public void searchTest()
    {
        Optional<A> byId = testARepository.findById(1L);
        byId.ifPresent(tempA -> {
            List<B> listB = tempA.getListB();
            listB.forEach(tempB -> log.info(tempB.getName()));
        });
    }

    private List<A> createA(int count)
    {
        List<A> listA = new ArrayList<>();

        for(int i = 0 ; i < count; i++)
        {
            A a = new A();
            a.setName("A"+i);
            listA.add(a);
        }

        return listA;
    }

    private List<B> createB(int count)
    {
        List<B> listB = new ArrayList<>();

        for(int i = 0 ; i < count; i++)
        {
            B b = new B();
            b.setName("B"+i);
            listB.add(b);
        }

        return listB;
    }
}