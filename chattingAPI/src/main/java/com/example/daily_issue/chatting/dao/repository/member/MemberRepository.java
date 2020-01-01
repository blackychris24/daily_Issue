package com.example.daily_issue.chatting.dao.repository.member;

import com.example.daily_issue.chatting.dao.repository.BaseRepository;
import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;

public interface MemberRepository extends BaseRepository<ChatMemberEntity, Long>
        , MemberCustomRepository {

    ChatMemberEntity findByUserId(String userId);
}
