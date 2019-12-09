package com.example.daily_issue.chatting.service;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-12-05
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-12-05)
 */

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Collection;

/**
 *
 *
 */
@Slf4j
abstract class ChatSecurityService<T extends UserDetails> {

    /*@Autowired
    SimpUserRegistry simpUserRegistry;*/


    Authentication getAuthentication(Principal wsPrincipal)
    {
        Authentication authentication = (Authentication)wsPrincipal;
        return authentication;
    }

    T getPrincipal(Principal wsPrincipal)
    {
        /*Class<T> genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), ChatSecurityService.class);
        T principal = getPrincipal(wsPrincipal, genericType);*/

        Authentication authentication = getAuthentication(wsPrincipal);
        T principal = (T)authentication.getPrincipal();

        return principal;
    }

    @SneakyThrows
    Authentication getUpdatedAuthentication(
            @NotNull Authentication originAuthentication
            , Class<? extends AbstractAuthenticationToken> clazz
            , @NotNull T fixedPrincipal) {

        Authentication fixedAuthentication = null;

        // UsernamePasswordAuthenticationToken
        if(clazz.isAssignableFrom(UsernamePasswordAuthenticationToken.class))
        {
            fixedAuthentication =
                    clazz.getConstructor(Object.class, Object.class, Collection.class)
                        .newInstance(fixedPrincipal
                                , originAuthentication.getCredentials()
                                , originAuthentication.getAuthorities());
            ((AbstractAuthenticationToken)fixedAuthentication).setDetails(originAuthentication.getDetails());
        }
        if(fixedAuthentication == null)
        {
            log.warn("The target principal object has not been initialized.");
            fixedAuthentication = originAuthentication;
        }

        return fixedAuthentication;
    }
}
