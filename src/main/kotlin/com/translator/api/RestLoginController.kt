package com.translator.api

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class RestLoginController {

    @GetMapping("/login/success/api")
    fun login(@AuthenticationPrincipal principal: OAuth2User): Map<String?, Any?>? {
        //save the data from login token and redirect to home
        return Collections.singletonMap("name", principal.getAttribute("name"))
    }
}