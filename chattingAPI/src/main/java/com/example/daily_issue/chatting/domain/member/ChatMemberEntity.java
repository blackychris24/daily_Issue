package com.example.daily_issue.chatting.domain.member;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-04
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-04)
 */

import com.example.daily_issue.chatting.domain.memberroom.ChatMemberRoomEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
@EqualsAndHashCode(exclude = "memberRoom")
@JsonIgnoreProperties("memberRoom")
public class ChatMemberEntity {

    @Id @GeneratedValue
    private Long id;

    private String userId;
    private String userPw;
    private String userName;
    private String userNickName;

    @OneToMany(mappedBy = "chatMember", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatMemberRoomEntity> memberRoom = new ArrayList<>();
}
