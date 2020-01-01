package com.example.daily_issue.chatting.dao.repository.member;

import com.example.daily_issue.chatting.dao.repository.room.RoomRepository;
import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.domain.participation.ChatParticipationEntity;
import com.example.daily_issue.chatting.domain.room.ChatRoomEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.stream.Collectors;
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
    public void getRoomMemberList1()
    {
        //createData();
        List<ChatRoomEntity> rooms = roomRepository.findAll();

        PageRequest pageRequest1 = PageRequest.of(0, 5, Sort.Direction.DESC, "userId");
        //List<ChatMemberEntity> chatMemberEntities1 = roomRepository.chatMemberList(rooms.get(0));
        Page<ChatMemberEntity> chatMemberEntities1 = memberRepository.roomMemberList(roomRepository.findById(1L).get(), pageRequest1);
        chatMemberEntities1.forEach( m -> {
            System.out.println(m.getUserId());
        });

        PageRequest pageRequest2 = PageRequest.of(0, 7, Sort.Direction.DESC, "userId");
        //List<ChatMemberEntity> chatMemberEntities2 = roomRepository.chatMemberList(rooms.get(1));
        Page<ChatMemberEntity> chatMemberEntities2 = memberRepository.roomMemberList(roomRepository.findById(2L).get(), pageRequest2);
        chatMemberEntities2.forEach( m -> {
            System.out.println(m.getUserId());
        });
    }

    @Test
    @DisplayName("방에 있는 사람들 목록 - room repo를 통하여서")
    public void getRoomMemberList2()
    {
        //createData();
        ChatRoomEntity room1 = roomRepository.findById(1L).get();
        List<ChatParticipationEntity> membersOfRoom1 = room1.getMembersOfRoom();
        for(ChatParticipationEntity temp : membersOfRoom1)
        {
            System.out.println(temp.getChatRoom().getRoomName() + " : " + temp.getChatMember().getUserName());
        }

        /*System.out.println("=========================================");

        ChatRoomEntity room2 = roomRepository.findById(2L).get();
        List<ChatParticipationEntity> membersOfRoom2 = room2.getMembersOfRoom();
        for(ChatParticipationEntity temp : membersOfRoom2)
        {
            System.out.println(temp.getChatRoom().getRoomName() + " : " + temp.getChatMember().getUserName());
        }*/
    }

    @Test
    @DisplayName("사용자와 방을 생성하고, 해당 방에 사용자가 입장함.")
    @Rollback(value = false)
    public void createRoom()
    {
        ChatMemberEntity chatMemberEntity = new ChatMemberEntity();
        chatMemberEntity.setUserName("test user name");
        chatMemberEntity.setUserPw("test user pw");
        chatMemberEntity.setUserNickName("test user nick");
        chatMemberEntity.setUserId("test user id");
        ChatMemberEntity savedMember = memberRepository.save(chatMemberEntity);


        ChatRoomEntity room = new ChatRoomEntity();
        room.setRoomName("test room");
        room.addMember(savedMember);

        System.out.println("============= SAVE ==============");
        ChatRoomEntity savedRoom = roomRepository.save(room);
        roomRepository.flush();


        System.out.println("============= FIND ==============");
        List<ChatParticipationEntity> membersOfTestRoom = savedRoom.getMembersOfRoom();
        for(ChatParticipationEntity temp : membersOfTestRoom)
        {
            System.out.println(temp.getChatRoom().getRoomName() + " : " + temp.getChatMember().getUserName());
        }
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

    @Test
    @DisplayName("특정 사용자의 방 목록 검색")
    public void getMemberRoomList()
    {
        //createData();
        //ChatMemberEntity member = memberRepository.findByUserId("member20 id");
        ChatMemberEntity member = memberRepository.findByUserId("id10");

        List<ChatRoomEntity> rooms = member.getRoomsOfMember().stream().map(p -> p.getChatRoom()).collect(Collectors.toList());
        rooms.forEach(r -> System.out.println(r.getRoomName()));
    }

    @Test
    @DisplayName("특정 방의 사용자 목록 검색")
    public void getRoomMemberList3()
    {
        //createData();
        //ChatMemberEntity member = memberRepository.findByUserId("member20 id");
        ChatRoomEntity room = roomRepository.findById(1L).get();

        List<ChatMemberEntity> members = room.getMembersOfRoom().stream().map(p -> p.getChatMember()).collect(Collectors.toList());
        members.forEach(m -> System.out.println(m.getUserId()));
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
            room1.addMember(tempMember);

            /*ChatParticipationEntity participationEntity = new ChatParticipationEntity();
            participationEntity.setChatRoom(room1);
            participationEntity.setChatMember(tempMember);

            tempMember.getRoomsOfMember().add(participationEntity);
            room1.getMembersOfRoom().add(participationEntity);*/

        }

        // for room 2
        ChatRoomEntity room2 = createRoom(2);
        roomRepository.save(room2);
        for(int i = 20 ; i <= 29 ; i ++)
        {
            ChatMemberEntity tempMember = createMember(i);
            room2.addMember(tempMember);

            /*ChatParticipationEntity participationEntity = new ChatParticipationEntity();
            participationEntity.setChatRoom(room2);
            participationEntity.setChatMember(tempMember);

            tempMember.getRoomsOfMember().add(participationEntity);
            room2.getMembersOfRoom().add(participationEntity);*/
        }
        roomRepository.flush();
    }

    private ChatRoomEntity createRoom(int id) {
        ChatRoomEntity room = new ChatRoomEntity();
        room.setRoomName("room - " + id);

        return room;
    }

    private ChatMemberEntity createMember(int id) {
        ChatMemberEntity member = new ChatMemberEntity();
        member.setUserId("id" + id);
        member.setUserPw("pw" + id);
        member.setUserName("member name : " + id);
        member.setUserNickName("member nick : " + id);

        return member;
    }
}