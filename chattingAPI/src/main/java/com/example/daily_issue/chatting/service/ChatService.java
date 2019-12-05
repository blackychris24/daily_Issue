package com.example.daily_issue.chatting.service;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-04
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-04)
 */

import com.example.daily_issue.chatting.dao.repository.member.MemberRepository;
import com.example.daily_issue.chatting.dao.repository.room.RoomRepository;
import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.domain.member.ChatMemberRESP;
import com.example.daily_issue.chatting.domain.room.ChatRoomEntity;
import com.example.daily_issue.chatting.domain.room.ChatRoomREQ;
import com.example.daily_issue.chatting.domain.room.ChatRoomRESP;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 */
@Service
public class ChatService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ChatRoomRESP> getRoomList()
    {
        List<ChatRoomEntity> rooms = roomRepository.findAll();
        //List<ChatRoomRESP> result = new ArrayList<>();


        List<ChatRoomRESP> result = rooms
                .stream()
                .map(
                        r -> modelMapper.map(r, ChatRoomRESP.class)
                ).collect(Collectors.toList());

        return result;
    }

    public List<ChatMemberRESP> getMemberListInRoom(ChatRoomREQ chatRoomREQ)
    {
        return getMemberListInRoom(chatRoomREQ, null);
    }

    public List<ChatMemberRESP> getMemberListInRoom(ChatRoomREQ chatRoomREQ, Pageable pageable)
    {
        List<ChatMemberEntity> members;
        members = (pageable != null ?
                memberRepository.roomMemberList(chatRoomREQ.getId(), pageable).getContent()
                : memberRepository.roomMemberList(chatRoomREQ.getId())
                );

        List<ChatMemberRESP> result = members
                .stream()
                .map(
                        m -> modelMapper.map(m, ChatMemberRESP.class)
                ).collect(Collectors.toList());

        return result;
    }

    /*public List<ChatRoomRESP> getRoomListOfMember()
    {

    }*/
}
