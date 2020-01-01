package com.example.daily_issue.chatting.domain.member;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-16
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-16)
 */

import lombok.Getter;

/**
 *
 *
 */
@Getter
public enum ChatMemberType {


    // TODO: 2019-12-16 주저리주저리...
    // 향후 해당 type을 security Role로 하여도 괜찮을까..?
    // 각 method에 @RolesAllowed나 @PreAuthorize 등으로 권한 체크 후 실행하게..
    // 가령, 사용자 추방, 방 삭제 등
    SYSTEMADMIN("시스템관리자"),
    MANAGER("방장"),
    SUBMANAGER("부방장"),
    USER("일반사용자");


    String typeName;
    ChatMemberType(String typeName)
    {
        this.typeName = typeName;
    }

}
