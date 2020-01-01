package com.example.daily_issue.chatting.domain.room;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-05
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-05)
 */

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 *
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ChatRoomREQ {

    private Long id;

    // 방 이름
    @NotNull @NotEmpty
    private String roomName;

    // 현재는 오픈채팅을 기준으로 작성한다.
    private ChatRoomType roomType = ChatRoomType.PUBLIC;

    // 채팅방 검색 가능여부
    private boolean isSearchable = true;

    // 채팅방 설정 최대인원
    @Min(1)
    private int defaultMemberNum = 100;
}
