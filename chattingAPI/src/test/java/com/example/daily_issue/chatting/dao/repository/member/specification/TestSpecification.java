package com.example.daily_issue.chatting.dao.repository.member.specification;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-13
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-13)
 */

import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */

public class TestSpecification implements Specification<ChatMemberEntity> {

    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) private LocalDate from;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) private LocalDate to;

    @Override
    public Predicate toPredicate(Root<ChatMemberEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (name != null) predicates.add(builder.like(builder.upper(root.get("name")), "%" + name.toUpperCase() + "%"));
        if (from != null) predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), from));
        if (to != null) predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), to));

        return null;
    }
}
