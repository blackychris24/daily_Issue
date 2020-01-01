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
import com.example.daily_issue.chatting.domain.member.ChatMemberREQ;
import com.example.daily_issue.chatting.domain.member.ChatMemberRESP;
import com.example.daily_issue.chatting.domain.room.ChatRoomEntity;
import com.example.daily_issue.chatting.domain.room.ChatRoomREQ;
import com.example.daily_issue.chatting.domain.room.ChatRoomRESP;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 *
 */
@Service
@Transactional(readOnly = true)
public class ChatRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    // 전체 방 목록 조회 (paging)
    public Page<ChatRoomRESP> getRoomList(Pageable pageable)
    {
        Page<ChatRoomEntity> rooms = roomRepository.findAll(pageable);
        Page<ChatRoomRESP> result = rooms
                .map(
                        r -> modelMapper.map(r, ChatRoomRESP.class)
                );

        return result;
    }

    // 특정 방의 사용자 목록 출력
    public List<ChatMemberRESP> getMemberListOfRoom(ChatRoomREQ chatRoomREQ)
    {
        List<ChatMemberEntity> members = memberRepository.roomMemberList(chatRoomREQ.getId());

        List<ChatMemberRESP> result = members
                .stream()
                .map(
                        m -> modelMapper.map(m, ChatMemberRESP.class)
                ).collect(Collectors.toList());

        return result;

        /*Optional<ChatRoomEntity> room = roomRepository.findById(chatRoomREQ.getId());

        List<ChatMemberRESP> result =
                // room 객체에서 participate list를 가져오고
                room.map(r -> r.getMembersOfRoom().stream()
                        // participate 객체를 순회하며, member entity 가져온 것을,
                        // 그 즉시 memberResp로 변환하여 return
                        .map(p -> modelMapper.map(p.getChatMember(), ChatMemberRESP.class))
                        .collect(Collectors.toList()))
                // 만일, 결과가 없을 경우, 빈 list 객체 반환
                .orElse(Collections.emptyList());

        return result;*/
    }

    // 특정 방의 사용자 목록 출력 (paging)
    public Page<ChatMemberRESP> getMemberListOfRoom(ChatRoomREQ chatRoomREQ, Pageable pageable)
    {
        Page<ChatMemberEntity> members = memberRepository.roomMemberList(chatRoomREQ.getId(), pageable);

        Page<ChatMemberRESP> result = members
                .map(
                        m -> modelMapper.map(m, ChatMemberRESP.class)
                );

        return result;
    }

    // 방을 생성함
    public ChatRoomRESP createRoom(ChatRoomREQ chatRoomREQ)
    {
        ChatRoomEntity entity = modelMapper.map(chatRoomREQ, ChatRoomEntity.class);
        entity = roomRepository.save(entity);

        ChatRoomRESP result = modelMapper.map(entity, ChatRoomRESP.class);

        return result;
    }

    // 방을 삭제함
    public void removeRoom(ChatRoomREQ chatRoomREQ)
    {
        ChatRoomEntity entity = modelMapper.map(chatRoomREQ, ChatRoomEntity.class);
        roomRepository.delete(entity);
    }

    // 방을 수정함
    public ChatRoomRESP modifyRoom(ChatRoomREQ chatRoomREQ)
    {
        ChatRoomRESP result = createRoom(chatRoomREQ);
        return result;
    }

    // 사용자를 추가함
    public void addMember(ChatRoomREQ chatRoomREQ, ChatMemberREQ chatMemberREQ)
    {
        // get entities
        Optional<ChatRoomEntity> roomEntity = roomRepository.findById(chatRoomREQ.getId());
        Optional<ChatMemberEntity> memberEntity = memberRepository.findById(chatMemberREQ.getId());

        roomEntity.ifPresent(r -> {
            memberEntity.ifPresent(m -> r.addMember(m));
        });
    }

    // 사용자를 삭제함
    public void removeMember(ChatRoomREQ chatRoomREQ, ChatMemberREQ chatMemberREQ)
    {
        // get entities
        Optional<ChatRoomEntity> roomEntity = roomRepository.findById(chatRoomREQ.getId());
        Optional<ChatMemberEntity> memberEntity = memberRepository.findById(chatMemberREQ.getId());

        roomEntity.ifPresent(r -> {
            memberEntity.ifPresent(m -> r.removeMember(m));
        });
    }
}
