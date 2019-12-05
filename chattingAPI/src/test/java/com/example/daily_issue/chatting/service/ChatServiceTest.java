package com.example.daily_issue.chatting.service;

import com.example.daily_issue.chatting.domain.room.ChatRoomRESP;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-05
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-05)
 */

/**
 *
 *
 */
@SpringBootTest
@Slf4j
class ChatServiceTest {

    @Autowired
    ChatService chatService;

    @Test
    @DisplayName("전체 방 목록")
    public void getRooms()
    {
        List<ChatRoomRESP> rooms = chatService.getRoomList();
        rooms.forEach(r -> log.info(r.toString()));
    }
}