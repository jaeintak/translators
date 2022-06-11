package com.translator.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthUser() : UserDetails {
    var no = 0L
    var roles = mutableSetOf<String>()

    constructor(no: Long, roles: List<String>) : this() {
        this.no = no
        this.roles.addAll(roles)
    }

    override fun getAuthorities() = roles.map { SimpleGrantedAuthority("ROLE_$it") }
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
    override fun getPassword() = null
    override fun getUsername() = null
}