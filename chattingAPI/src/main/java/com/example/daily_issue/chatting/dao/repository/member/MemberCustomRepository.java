package com.example.daily_issue.chatting.dao.repository.member;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-11
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-11)
 */

import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.domain.room.ChatRoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *
 *
 */
interface MemberCustomRepository {

    List<ChatMemberEntity> roomMemberList(Long chatRoomId);
    List<ChatMemberEntity> roomMemberList(ChatRoomEntity chatRoomEntity);

    Page<ChatMemberEntity> roomMemberList(Long chatRoomId, Pageable pageable);
    Page<ChatMemberEntity> roomMemberList(ChatRoomEntity chatRoomEntity, Pageable pageable);
}
