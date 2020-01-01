package com.example.daily_issue.chatting.service;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-13
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-13)
 */

import com.example.daily_issue.chatting.dao.repository.member.MemberRepository;
import com.example.daily_issue.chatting.dao.repository.room.RoomRepository;
import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.domain.member.ChatMemberREQ;
import com.example.daily_issue.chatting.domain.member.ChatMemberRESP;
import com.example.daily_issue.chatting.domain.room.ChatRoomEntity;
import com.example.daily_issue.chatting.domain.room.ChatRoomRESP;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 */
@Service
@Transactional(readOnly = true)
public class ChatMemberService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ModelMapper modelMapper;

    // 특정 사용자의 방 목록 출력
    public List<ChatRoomRESP> getRoomListOfMember(ChatMemberREQ chatMemberREQ)
    {
        List<ChatRoomEntity> rooms = roomRepository.memberRoomList(chatMemberREQ.getId());

        List<ChatRoomRESP> result = rooms
                .stream()
                .map(
                        r -> modelMapper.map(r, ChatRoomRESP.class)
                ).collect(Collectors.toList());

        return result;

        /*Optional<ChatMemberEntity> member = memberRepository.findById(chatMemberREQ.getId());

        List<ChatRoomRESP> result =
                // member 객체에서 participate list를 가져오고,
                member.map(m -> m.getRoomsOfMember().stream()
                        // participate 객체를 순회하며, room entity 가져 온 것을,
                        // 그 즉시 roomResp로 변환하여 return
                        .map(p -> modelMapper.map(p.getChatRoom(), ChatRoomRESP.class))
                        .collect(Collectors.toList()))
                // 만일, 결과가 없을 경우, 빈 list 객체 반환
                .orElse(Collections.emptyList());

        return result;*/
    }

    // 특정 사용자의 방 목록 출력 (paging)
    public Page<ChatRoomRESP> getRoomListOfMember(ChatMemberREQ chatMemberREQ, Pageable pageable)
    {
        Page<ChatRoomEntity> rooms = roomRepository.memberRoomList(chatMemberREQ.getId(), pageable);

        Page<ChatRoomRESP> result = rooms
                .map(
                        r -> modelMapper.map(r, ChatRoomRESP.class)
                );

        return result;
    }

    // 사용자를 생성함
    public ChatMemberRESP createMember(ChatMemberREQ chatMemberREQ)
    {
        ChatMemberEntity entity = modelMapper.map(chatMemberREQ, ChatMemberEntity.class);
        entity = memberRepository.save(entity);

        ChatMemberRESP result = modelMapper.map(entity, ChatMemberRESP.class);

        return result;
    }

    // 사용자를 삭제함
    public void removeMember(ChatMemberREQ chatMemberREQ)
    {
        ChatMemberEntity entity = modelMapper.map(chatMemberREQ, ChatMemberEntity.class);
        memberRepository.delete(entity);
    }

    // 사용자를 수정함
    public ChatMemberRESP modifyMember(ChatMemberREQ chatMemberREQ)
    {
        ChatMemberRESP result = createMember(chatMemberREQ);
        return result;
    }
}
