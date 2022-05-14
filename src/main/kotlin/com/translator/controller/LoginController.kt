package com.translator.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.util.*


@Controller
class LoginController {

    @GetMapping("/login")
    fun user(@AuthenticationPrincipal principal: OAuth2User): Map<String?, Any?>? {
        return Collections.singletonMap("name", principal.getAttribute("name"))
    }
}