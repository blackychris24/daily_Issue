package com.example.daily_issue.chatting.dao.repository.room;

import com.example.daily_issue.chatting.dao.repository.BaseRepository;
import com.example.daily_issue.chatting.domain.room.ChatRoomEntity;

public interface RoomRepository extends BaseRepository<ChatRoomEntity, Long>, RoomCustomRepository {

    /*public List<BasicTaskEntity> findByTaskOwner(Member user);*/


}
