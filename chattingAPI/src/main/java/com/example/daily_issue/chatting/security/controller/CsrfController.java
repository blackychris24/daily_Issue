package com.example.daily_issue.chatting.security.controller;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-28
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-28)
 */

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 */
@RestController
@RequestMapping("chatting")
public class CsrfController {

    @GetMapping("csrf")
    public CsrfToken csrfToken(CsrfToken csrfToken)
    {
        return csrfToken;
    }
}
