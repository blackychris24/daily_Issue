package com.example.daily_issue.chatting.service;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-07
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-07)
 */

import com.example.daily_issue.chatting.security.domain.LoginUser;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 *
 *
 */
@Service
public class ChatLoginUserSecurityService extends ChatSecurityService<LoginUser>
{
    // 사용자 정보 획득
    public LoginUser getPrincipal(StompHeaderAccessor headerAccessor) {
        LoginUser principal = super.getPrincipal(headerAccessor.getUser());

        return principal;
    }

    // 사용자 정보 획득
    @Override
    public LoginUser getPrincipal(Principal wsUser) {
        return super.getPrincipal(wsUser);
    }

    // 사용자 정보를 update 함
    @SneakyThrows
    public void updatePrincipal(StompHeaderAccessor headerAccessor, LoginUser fixedPrincipal)
    {
        Authentication originAuthentication = getAuthentication(headerAccessor.getUser());

        String classname = originAuthentication.getClass().getName();

        Principal updatedAuthentication = super.getUpdatedAuthentication(
                originAuthentication
                , (Class<? extends AbstractAuthenticationToken>) Class.forName(originAuthentication.getClass().getName())
                , fixedPrincipal);

        updateAuthentication(headerAccessor, updatedAuthentication);
    }


    // 방 번호를 update 함
    public void updateRoomId(StompHeaderAccessor headerAccessor, Long roomId)
    {
        if(hasParticipatingRoom(headerAccessor))
        {
            throw new RuntimeException("There are rooms that are already participating.");
        }

        LoginUser principal = super.getPrincipal(headerAccessor.getUser());
        principal.setCurrentRoomId(roomId);

        updatePrincipal(headerAccessor, principal);
    }


    // 현재 참여중인 방이 있는가?
    public boolean hasParticipatingRoom(StompHeaderAccessor headerAccessor)
    {
        LoginUser principal = super.getPrincipal(headerAccessor.getUser());
        boolean hasRoom = (principal.getCurrentRoomId() != null && principal.getCurrentRoomId() >= 0);

        return hasRoom;
    }

    // websocket User (Authentication)을 update 함
    private void updateAuthentication(StompHeaderAccessor headerAccessor, Principal updatedAuthentication)
    {
        headerAccessor.setUser(updatedAuthentication);
        SecurityContextHolder.getContext().setAuthentication((Authentication)updatedAuthentication);
    }
}
