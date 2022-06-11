package com.translator.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class LoginController {

    @GetMapping("/")
    fun getLoginPage(): String {
        return "login"
    }

    @Autowired
    private val authorizedClientService: OAuth2AuthorizedClientService? = null

    @GetMapping("/login/success")
    fun getLoginInfo(model: Model?, authentication: OAuth2AuthenticationToken): String? {
        val client = authorizedClientService
            ?.loadAuthorizedClient<OAuth2AuthorizedClient>(
                authentication.authorizedClientRegistrationId,
                authentication.name
            )
        //...
        return "home"
    }
}