package com.example.daily_issue.calendar.ro;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class TaskResp {

    /* task pk */
    private Long id;

    /* task creator */
    private Long createdBy;

    /* task created date */
    private LocalDateTime createdDate;

    /* task modifier */
    private Long lastModifiedBy;

    /* task last modified date */
    private LocalDateTime lastModifiedDate;


    // 지금은 사용하지 않는다.
    /*private String taskPerformerId;*/

    /* 일정 종일여부
    = 일 단위로 일정관리 여부
    ( true : 시간정보 제외됨) */
    private Boolean isAllDay;

    /* 일정 시작일시 */
    private LocalDateTime startDateTime;

    /* 일정 종료일시 */
    private LocalDateTime endDateTime;

    /* 일정 제목 */
    private String title;

    /* 일정 상세설명 */
    private String comment;

    /* 일정 위치 / 장소 */
    private String place;


    /* 일정 표기 색 (default : black) */
    /*
     * rgb : rgb(0, 0, 0)
     * cmyk : cmyk(0%, 0%, 0%, 100%)
     * hsl : hsl(0, 0%, 0%)
     * hex : #000000
     * */
    private String color;

}