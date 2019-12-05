package com.example.daily_issue.chatting.security.domain;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-05
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-05)
 */

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 *
 */
@Getter
@Setter
public class LoginUser extends User {


    // 현재 활동중인 방 id / 음수일 경우 할당되지 않음
    private Long currentRoomId = -1L;

    private String userName;
    private String userNickName;

    public LoginUser(String username, String password, String userName, String userNickName)
    {
        super(username, password, Lists.newArrayList(new SimpleGrantedAuthority("ROLE_USER")));
        this.userName = userName;
        this.userNickName = userNickName;
    }
}
