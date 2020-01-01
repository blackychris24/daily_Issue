package com.example.daily_issue.chatting.domain.member;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-05
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-05)
 */

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 *
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ChatMemberREQ {

    private Long id;

    // 사용자 아이디
    private String userId;

    // 사용자 패스워드
    private String userPw;

    // 사용자 성명
    private String userName;

    // 사용자 활동명
    private String userNickName;

    // 사용자 종류not
    private ChatMemberType chatMemberType = ChatMemberType.USER;

}
