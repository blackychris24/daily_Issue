package com.example.daily_issue.chatting.dao.repository.member;

import com.example.daily_issue.chatting.dao.repository.room.RoomRepository;
import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.domain.memberroom.ChatMemberRoomEntity;
import com.example.daily_issue.chatting.domain.room.ChatRoomEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-04
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-04)
 */

/**
 *
 *
 */
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RoomRepository roomRepository;

    @Test
    @DisplayName("방에 들어있는 사람들 목록")
    public void getRoomMemberList()
    {
        //createData();
        List<ChatRoomEntity> rooms = roomRepository.findAll();

        PageRequest pageRequest1 = PageRequest.of(0, 5, Sort.Direction.DESC, "userId");
        //List<ChatMemberEntity> chatMemberEntities1 = roomRepository.chatMemberList(rooms.get(0));
        Page<ChatMemberEntity> chatMemberEntities1 = memberRepository.roomMemberList(rooms.get(0), pageRequest1);
        chatMemberEntities1.forEach( m -> {
            System.out.println(m.getUserId());
        });

        PageRequest pageRequest2 = PageRequest.of(0, 7, Sort.Direction.DESC, "userId");
        //List<ChatMemberEntity> chatMemberEntities2 = roomRepository.chatMemberList(rooms.get(1));
        Page<ChatMemberEntity> chatMemberEntities2 = memberRepository.roomMemberList(rooms.get(1), pageRequest2);
        chatMemberEntities2.forEach( m -> {
            System.out.println(m.getUserId());
        });
    }


    @Test
    @DisplayName("전체 사용자 검색")
    public void getAllMemberList()
    {
        List<ChatMemberEntity> members = memberRepository.findAll();
        members.forEach(System.out::println);
    }

    @Test
    @DisplayName("특정 사용자 id 검색")
    public void getSpecificUser()
    {
        ChatMemberEntity member = memberRepository.findByUserId("id10");
        System.out.println(member);
    }






    /////////////////////////////////////////////////////////////////
    //////////////////////////// UTILITY ////////////////////////////
    /////////////////////////////////////////////////////////////////
    @DisplayName("임시 데이터 생성")
    private void createData() {
        // for room 1
        ChatRoomEntity room1 = createRoom(1);
        roomRepository.save(room1);
        for(int i = 10 ; i <= 19 ; i ++)
        {
            ChatMemberEntity tempMember = createMember(i);

            ChatMemberRoomEntity chatMemberRoomEntity = new ChatMemberRoomEntity();
            chatMemberRoomEntity.setChatRoom(room1);
            chatMemberRoomEntity.setChatMember(tempMember);

            room1.getMemberRoom().add(chatMemberRoomEntity);
        }

        // for room 2
        ChatRoomEntity room2 = createRoom(2);
        roomRepository.save(room2);
        for(int i = 20 ; i <= 29 ; i ++)
        {
            ChatMemberEntity tempMember = createMember(i);

            ChatMemberRoomEntity chatMemberRoomEntity = new ChatMemberRoomEntity();
            chatMemberRoomEntity.setChatRoom(room2);
            chatMemberRoomEntity.setChatMember(tempMember);

            room2.getMemberRoom().add(chatMemberRoomEntity);
        }
    }

    private ChatRoomEntity createRoom(int id) {
        ChatRoomEntity room = new ChatRoomEntity();
        room.setRoomName("Room - " + id);

        return room;
    }

    private ChatMemberEntity createMember(int id) {
        ChatMemberEntity member = new ChatMemberEntity();
        member.setUserId("member" + id + " id");
        member.setUserPw("member" + id + " pw");
        member.setUserName("member" + id + " Name");
        member.setUserNickName("member" + id + " Nickname");

        return member;
    }
}