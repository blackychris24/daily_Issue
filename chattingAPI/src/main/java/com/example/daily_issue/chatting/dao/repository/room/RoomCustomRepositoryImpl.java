package com.example.daily_issue.chatting.dao.repository.room;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-11
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-11)
 */

import com.example.daily_issue.chatting.dao.repository.QueryDslBaseRepositorySupport;
import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.domain.room.ChatRoomEntity;
import com.example.daily_issue.chatting.domain.room.ChatRoomType;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

import static com.example.daily_issue.chatting.domain.participation.QChatParticipationEntity.chatParticipationEntity;
import static com.example.daily_issue.chatting.domain.room.QChatRoomEntity.chatRoomEntity;

/**
 *
 *
 */
@NoRepositoryBean
class RoomCustomRepositoryImpl extends QueryDslBaseRepositorySupport implements RoomCustomRepository {

    public RoomCustomRepositoryImpl() {
        super(ChatRoomEntity.class);
    }


    @Override
    public List<ChatRoomEntity> memberRoomList(Long chatMemberId) {
        JPQLQuery<ChatRoomEntity> query = getChatRoomListQuery(chatMemberId);
        List<ChatRoomEntity> rooms = query.fetch();

        return rooms;
    }

    @Override
    public Page<ChatRoomEntity> memberRoomList(Long chatMemberId, Pageable pageable) {
        JPQLQuery<ChatRoomEntity> query = getChatRoomListQuery(chatMemberId);
        Page<ChatRoomEntity> result = readPage(query, pageable);

        return result;
    }

    @Override
    public List<ChatRoomEntity> memberRoomList(ChatMemberEntity chatMemberEntity) {
        return memberRoomList(chatMemberEntity.getId());
    }

    @Override
    public Page<ChatRoomEntity> memberRoomList(ChatMemberEntity chatMemberEntity, Pageable pageable) {
        return memberRoomList(chatMemberEntity.getId(), pageable);
    }










    private JPQLQuery<ChatRoomEntity> getChatRoomListQuery(Long chatMemberId) {
        JPQLQuery<ChatRoomEntity> query =
                from(chatRoomEntity)
                        .join(chatParticipationEntity)
                        .on(
                                chatParticipationEntity.chatMember.id.eq(chatMemberId)
                                        .and(chatParticipationEntity.chatRoom.id.eq(chatRoomEntity.id))
                        )
                        .where(
                                chatRoomEntity.roomType.eq(ChatRoomType.PUBLIC)
                                .and(chatRoomEntity.isSearchable.isTrue())
                        )
                        .select(chatRoomEntity)
                        .fetchAll();

        return query;
    }
}
