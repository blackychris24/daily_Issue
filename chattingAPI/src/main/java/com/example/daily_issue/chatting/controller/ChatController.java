package com.example.daily_issue.chatting.controller;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-26
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-26)
 */

import com.example.daily_issue.chatting.domain.room.ChatRoomRESP;
import com.example.daily_issue.chatting.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    ChatRoomService chatService;

    // return Page<>로 return하려고 해도.. FE에서 손대는게 겁낭...ㅠㅠ
    // 일단은 List<>로 반환함.
    @GetMapping("rooms")
    public List<ChatRoomRESP> roomList(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable)
    {
        Page<ChatRoomRESP> rooms = chatService.getRoomList(pageable);
        List<ChatRoomRESP> result = rooms.getContent();

        return result;
    }

}
