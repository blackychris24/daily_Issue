package com.example.daily_issue.chatting.dao.repository.member;

import com.example.daily_issue.chatting.dao.repository.BaseRepository;
import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.domain.member.QChatMemberEntity;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface MemberRepository extends BaseRepository<ChatMemberEntity, Long>
        , MemberCustomRepository
        , QuerydslPredicateExecutor<ChatMemberEntity>, QuerydslBinderCustomizer<QChatMemberEntity>
        , JpaSpecificationExecutor<ChatMemberEntity> {

    @Override
    default void customize(QuerydslBindings bindings, QChatMemberEntity entity) {
        // Make case-insensitive 'like' filter for all string properties
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);

        //bindings.bind(entity.id).first((path, value) -> path.eq(value));

        //bindings.bind(entity.userPw).all((path, value) -> Optional.empty());
        bindings.excluding(entity.userPw);

        // Add 'between' and 'greater or equal' filter date property
        // name=john&createdAt=2019-04-15&createdAt=2019-04-19
        /*bindings.bind(entity.createdAt).all((path, value) -> {
            Iterator<? extends LocalDate> it = value.iterator();
            LocalDate from = it.next();
            if (value.size() >= 2) {
                LocalDate to = it.next();
                return Optional.of(path.between(from, to)); // between
            } else {
                return Optional.of(path.goe(from)); // greater than or equal
            }
        });*/
    }

    ChatMemberEntity findByUserId(String userId);
}
