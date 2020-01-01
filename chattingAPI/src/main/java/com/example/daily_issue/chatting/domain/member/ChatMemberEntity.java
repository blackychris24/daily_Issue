package com.example.daily_issue.chatting.domain.member;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-04
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-04)
 */

import com.example.daily_issue.chatting.domain.participation.ChatParticipationEntity;
import com.example.daily_issue.chatting.domain.room.ChatRoomEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
@Entity
@Getter
@Setter
@BatchSize(size = 100)
@EqualsAndHashCode(exclude = "memberRoom")
@JsonIgnoreProperties("memberRoom")
public class ChatMemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 아이디
    private String userId;

    // 사용자 패스워드
    private String userPw;

    // 사용자 성명
    private String userName;

    // 사용자 활동명
    private String userNickName;

    // 사용자 종류
    @Enumerated(EnumType.STRING)
    private ChatMemberType chatMemberType = ChatMemberType.USER;



    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "chatMember", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatParticipationEntity> roomsOfMember = new ArrayList<>();

    public void addRoom(ChatRoomEntity chatRoomEntity)
    {
        ChatParticipationEntity participationEntity = new ChatParticipationEntity();
        participationEntity.setChatRoom(chatRoomEntity);
        participationEntity.setChatMember(this);

        this.getRoomsOfMember().add(participationEntity);
        chatRoomEntity.getMembersOfRoom().add(participationEntity);
    }

    public void removeRoom(ChatRoomEntity chatRoomEntity)
    {
        ChatParticipationEntity participationEntity = new ChatParticipationEntity();
        participationEntity.setChatRoom(chatRoomEntity);
        participationEntity.setChatMember(this);

        this.getRoomsOfMember().remove(participationEntity);
        chatRoomEntity.getMembersOfRoom().remove(participationEntity);
    }
}
