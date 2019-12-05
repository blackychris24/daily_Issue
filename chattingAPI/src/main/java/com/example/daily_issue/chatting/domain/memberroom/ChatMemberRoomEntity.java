package com.example.daily_issue.chatting.domain.memberroom;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-04
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-04)
 */

import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.domain.room.ChatRoomEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 *
 *
 */
@Entity
@Getter
@Setter
public class ChatMemberRoomEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_ID")
    private ChatRoomEntity chatRoom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private ChatMemberEntity chatMember;
}
