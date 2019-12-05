package com.example.daily_issue.chatting.dao.repository.member;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-11
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-11)
 */

import com.example.daily_issue.chatting.domain.member.ChatMemberEntity;
import com.example.daily_issue.chatting.domain.member.QChatMemberEntity;
import com.example.daily_issue.chatting.domain.memberroom.QChatMemberRoomEntity;
import com.example.daily_issue.chatting.domain.room.ChatRoomEntity;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

/**
 *
 *
 */
class MemberCustomRepositoryImpl extends QuerydslRepositorySupport implements MemberCustomRepository {

    public MemberCustomRepositoryImpl() {
        super(ChatMemberEntity.class);
    }

    @Override
    public List<ChatMemberEntity> roomMemberList(Long chatRoomId) {
        JPQLQuery<ChatMemberEntity> query = getChatMemberListQuery(chatRoomId);
        List<ChatMemberEntity> members = query.fetch();

        return members;
    }

    @Override
    public Page<ChatMemberEntity> roomMemberList(Long chatRoomId, Pageable pageable) {
        JPQLQuery<ChatMemberEntity> query = getChatMemberListQuery(chatRoomId);
        List<ChatMemberEntity> members = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(members, pageable, query.fetchCount());
    }

    @Override
    public List<ChatMemberEntity> roomMemberList(ChatRoomEntity chatRoomEntity) {
        return roomMemberList(chatRoomEntity.getId());
    }

    @Override
    public Page<ChatMemberEntity> roomMemberList(ChatRoomEntity chatRoomEntity, Pageable pageable) {
        return roomMemberList(chatRoomEntity.getId(), pageable);
    }




    private JPQLQuery<ChatMemberEntity> getChatMemberListQuery(Long chatRoomId) {
        QChatMemberRoomEntity chatMemberRoomEntity = QChatMemberRoomEntity.chatMemberRoomEntity;
        QChatMemberEntity chatMemberEntity = QChatMemberEntity.chatMemberEntity;

        JPQLQuery<ChatMemberEntity> query =
                from(chatMemberEntity)
                        .join(chatMemberRoomEntity)
                        .on(
                                chatMemberRoomEntity.chatRoom.id.eq(chatRoomId)
                                        .and(chatMemberRoomEntity.chatMember.id.eq(chatMemberEntity.id))
                        )
                        //.orderBy(chatMemberEntity.userId.asc())
                        .select(chatMemberEntity)
                        .fetchAll();

        return query;
    }



    /*@Autowired
    SecurityService securityService;

    public RoomCustomRepositoryImpl() {
        super(BasicTaskEntity.class);
    }

    @Override
    public List<BasicTaskEntity> findByDisplayableBasicTasks(DateRange dateRange) {

        // basic task
        QBasicTaskEntity basicTaskEntity = QBasicTaskEntity.basicTaskEntity;

        List<BasicTaskEntity> basicTasks =
            from(basicTaskEntity)
            .where(
                basicTaskEntity.taskOwner.eq(securityService.getMember())
                .and(
                    basicTaskEntity.taskStartDate.between(dateRange.getStartDate(), dateRange.getEndDate())
                )
            )
            .fetch();

        return basicTasks;
    }

    @Override
    public List<RepeatableTaskEntity> findByDisplayableRepeatableTasks(DateRange dateRange) {

        // repeatable task
        QRepeatableTaskEntity repeatableTaskEntity = QRepeatableTaskEntity.repeatableTaskEntity;
        QBasicTaskEntity basicTaskEntity = QBasicTaskEntity.basicTaskEntity;

        List<RepeatableTaskEntity> repeatableTasks =
            from(repeatableTaskEntity)
            .innerJoin(repeatableTaskEntity.basicTask, basicTaskEntity)
            .fetchJoin()
            .where(
                repeatableTaskEntity.basicTask.taskOwner.eq(securityService.getMember())
                .and(
                    //repeatableTask.repeatStartDate.before(dateRange.getEndDate())
                    repeatableTaskEntity.repeatStartDate.loe(dateRange.getEndDate())
                )
                .and(
                    repeatableTaskEntity.repeatEndDate.isNull()
                    //.or(repeatableTask.repeatEndDate.after(dateRange.getStartDate()))
                    .or(repeatableTaskEntity.repeatEndDate.goe(dateRange.getStartDate()))
                    )
                )
            .fetch();

        return repeatableTasks;
    }*/


}
