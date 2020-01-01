package com.example.daily_issue.chatting.config.websocket;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-26
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-26)
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 */
public interface MessageURIConsts {

    String ENDPOINT = "/ws";
    String APPLICATION = "/app";
    String TOPIC_CHAT_CONTEXT = "/chatting/room";
    String TOPIC = APPLICATION + TOPIC_CHAT_CONTEXT;
    String CHAT = "/chat";

    default String getRoomURI(Long roomId)
    {
        return TOPIC + "/" + roomId + CHAT;
    }

    default Long getRoomId(String requestUri)
    {
        String checkRegexString = TOPIC + "/" + "\\d+" + CHAT;
        Pattern checkRegex = Pattern.compile(checkRegexString);
        Matcher matcher = checkRegex.matcher(requestUri);
        if(matcher.find())
        {
            matcher = Pattern.compile("\\d+").matcher(requestUri);
            String result = matcher.group();
            return Long.valueOf(result);
        }
        return null;
    }

}
