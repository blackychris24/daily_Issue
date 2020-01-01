package com.example.daily_issue.chatting.controller;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-13
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-13)
 */

import com.example.daily_issue.chatting.dao.repository.member.MemberRepository;
import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.domain.member.ChatMemberRESP;
import com.querydsl.core.types.Predicate;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 */
@RestController
@RequestMapping("predicate")
public class PredicateTestController {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("memberTest1")
    public Page<ChatMemberRESP> memberTest1(@QuerydslPredicate(root = ChatMemberEntity.class) Predicate predicate, Pageable pageable)
    {
        Page<ChatMemberEntity> all = memberRepository.findAll(predicate, pageable);
        Page<ChatMemberRESP> result = all.map(m -> modelMapper.map(m, ChatMemberRESP.class));

        return result;
    }

    @GetMapping("memberTest2")
    // https://github.com/tkaczmarzyk/specification-arg-resolver#and
    // https://cnpnote.tistory.com/entry/SPRING-JPA-%EB%B0%8F-Spring-%EB%B6%80%ED%8A%B8%EC%97%90-%EB%8C%80%ED%95%9C-%EA%B2%80%EC%83%89-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84-%EB%8B%AB%EA%B8%B0
    public Page<ChatMemberRESP> memberTest2(@And({
            @Spec(path = "name", spec = LikeIgnoreCase.class),
            @Spec(path = "createdAt", params = "from", spec = GreaterThanOrEqual.class),
            @Spec(path = "createdAt", params = "to", spec = LessThanOrEqual.class)
    }) Specification<ChatMemberEntity> specification, Pageable pageable) {

        Page<ChatMemberEntity> all = memberRepository.findAll(specification, pageable);
        Page<ChatMemberRESP> result = all.map(m -> modelMapper.map(m, ChatMemberRESP.class));

        return result;
    }
}
