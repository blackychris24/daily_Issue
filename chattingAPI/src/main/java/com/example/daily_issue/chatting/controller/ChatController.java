package com.example.daily_issue.chatting.controller;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-26
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-26)
 */

import com.example.daily_issue.chatting.domain.room.ChatRoomRESP;
import com.example.daily_issue.chatting.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 *
 */
@RestController
@RequestMapping("chatting")
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping("rooms")
    public List<ChatRoomRESP> roomList(Model model)
    {
        List<ChatRoomRESP> rooms = chatService.getRoomList();
        return rooms;
    }
}
