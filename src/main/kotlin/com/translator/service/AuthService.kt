package com.translator.service

import com.translator.model.AuthUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthService (

): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return AuthUser(1, listOf("ADMIN", "USER"))
    }

    fun login() = AuthUser(1, listOf("ADMIN", "USER"))
}