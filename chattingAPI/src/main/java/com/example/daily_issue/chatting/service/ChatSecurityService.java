package com.example.daily_issue.chatting.service;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-05
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-05)
 */

import com.example.daily_issue.chatting.security.domain.LoginUser;
import com.example.daily_issue.chatting.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 */
@Service
public class ChatSecurityService<T> {

    @Autowired
    SecurityService securityService;

    public void updateRoomId(Long roomId)
    {
        LoginUser principal = securityService.getPrincipal();
        principal.setCurrentRoomId(roomId);
        securityService.updatePrincipal(principal);
    }

    public boolean isFisrtVisit()
    {
        LoginUser principal = securityService.getPrincipal();
        return principal.getCurrentRoomId() < 0;
    }

    public boolean isCorrectRoomId(Long roomId)
    {
        LoginUser principal = securityService.getPrincipal();

        // 방 id가 음수 : 방 번호가 할당되지 않음
        // 방 id가 양수이며 / 요청 방번호가 방입장 시 등록된 id와 동일하면 유효한 방 id가 맞음
        return principal.getCurrentRoomId() >= 0 && principal.getCurrentRoomId() == roomId ? true : false;
    }

    /*public T convertWSPrincipal(Principal principal)
    {
        principal
    }*/
}
