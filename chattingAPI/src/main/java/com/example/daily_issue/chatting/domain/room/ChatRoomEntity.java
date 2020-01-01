package com.example.daily_issue.chatting.domain.room;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-04
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-04)
 */

import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.domain.participation.ChatParticipationEntity;
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
public class ChatRoomEntity {

    @Setter(AccessLevel.PRIVATE)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 방 이름
    private String roomName;

    // 현재는 오픈채팅을 기준으로 작성한다.
    @Enumerated(EnumType.STRING)
    private ChatRoomType roomType = ChatRoomType.PUBLIC;

    // 채팅방 검색 가능여부
    private boolean isSearchable = true;

    // 채팅방 기본 최대인원
    private int defaultMemberNum = 100;

    // 채팅방 최대 최대인원
    private int maxMemberNum = 1500;



    /*
    o 채팅방 이름
    o 채팅방 종류 (그룹채팅방 / 비밀채팅방 / 1:1 채팅방)
    x 프로필 사용 여부
    o 채팅방 검색 가능 여부
    o 채팅방 최대 인원수 :100 단위 / 1500까지
    x 참여코드 : 방 입장 시 참여코드가 일치해야 함
    x 내보내기 해제 : 강퇴한 사람을 해제할 수 있음?
    x 권한관리 : 방장을 변경할 수 있음 / 부반장 설정가능
    x 링크정보 : 방의 링크를 생성하여 초대할 수 있음
    x 커버보기 : 방 대표 이미지 변경
    x 채팅방 소개 : 해쉬태그로 설정가능, 최대 80자
     */

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatParticipationEntity> membersOfRoom = new ArrayList<>();

    public void addMember(ChatMemberEntity chatMemberEntity)
    {
        ChatParticipationEntity participationEntity = new ChatParticipationEntity();
        participationEntity.setChatRoom(this);
        participationEntity.setChatMember(chatMemberEntity);

        this.getMembersOfRoom().add(participationEntity);
        chatMemberEntity.getRoomsOfMember().add(participationEntity);
    }

    public void removeMember(ChatMemberEntity chatMemberEntity)
    {
        ChatParticipationEntity participationEntity = new ChatParticipationEntity();
        participationEntity.setChatRoom(this);
        participationEntity.setChatMember(chatMemberEntity);

        this.getMembersOfRoom().remove(participationEntity);
        chatMemberEntity.getRoomsOfMember().remove(participationEntity);
    }
}
