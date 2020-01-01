package com.example.daily_issue.chatting.domain.participation;/**
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
public class ChatParticipationEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_ID")
    private ChatRoomEntity chatRoom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private ChatMemberEntity chatMember;

    // TODO: 2019-12-13 participate entity 역할 주저리주저리
    // 해당 방의 해당 사용자가 어디까지 message를 읽었는지?
    // 해당 방의 해당 사용자가...

    // 해당 사용자가 해당 방에서... 몇번 경고받았는지?
    // 해당 사용자가 해당 방의... 방장?

}
