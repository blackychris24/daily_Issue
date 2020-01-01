package com.example.daily_issue.chatting.domain.room;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-13
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-13)
 */

import lombok.Getter;

/**
 *
 *
 */
@Getter
public enum ChatRoomType {

    PUBLIC("오픈채팅", 1500),
    PRIVATE("비밀채팅", 1),
    STANDARD("일반채팅", 1500);

    private String typeName;
    private int maxMember;
    ChatRoomType(String typeName, int maxMember)
    {
        this.typeName = typeName;
        this.maxMember = maxMember;
    }
}
