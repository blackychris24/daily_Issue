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
public class ChatMemberRESP {

    private Long id;

    private String userId;
    private String userPw;
    private String userName;
    private String userNickName;

}
