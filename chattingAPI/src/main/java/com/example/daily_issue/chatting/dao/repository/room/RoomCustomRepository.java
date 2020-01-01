package com.example.daily_issue.chatting.dao.repository.room;/**
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
interface RoomCustomRepository {

    List<ChatRoomEntity> memberRoomList(Long chatMemberId);
    List<ChatRoomEntity> memberRoomList(ChatMemberEntity chatMemberEntity);

    Page<ChatRoomEntity> memberRoomList(Long chatMemberId, Pageable pageable);
    Page<ChatRoomEntity> memberRoomList(ChatMemberEntity chatMemberEntity, Pageable pageable);

}
