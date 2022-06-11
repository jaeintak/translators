package com.translator.token

import io.jsonwebtoken.security.Keys
import lombok.extern.slf4j.Slf4j
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.security.Key
import java.util.*
import java.util.stream.Collectors


@Slf4j
class AuthTokenProvider(secret: String) {

    private val key: Key? = Keys.hmacShaKeyFor(secret.toByteArray())
    private val AUTHORITIES_KEY = "role"

    fun createAuthToken(id: String?, expiry: Date?): AuthToken {
        return AuthToken(id!!, null, expiry, key!!)
    }

    fun createAuthToken(id: String?, role: String?, expiry: Date?): AuthToken {
        return AuthToken(id!!, role, expiry, key!!)
    }

    fun convertAuthToken(token: String?): AuthToken {
        return AuthToken(token!!, null, null, key!!)
    }

//    fun getAuthentication(authToken: AuthToken): Authentication? {
//        return if (authToken.validate()) {
//            val claims = authToken.getTokenClaims()
//            val authorities: Collection<GrantedAuthority?> = Arrays.stream(
//                arrayOf(
//                    claims!![AUTHORITIES_KEY].toString()
//                )
//            )
//                .map { role: String? -> SimpleGrantedAuthority(role) }
//                .collect(Collectors.toList())
//            log.debug("claims subject := [{}]", claims.subject)
//            val principal = User(claims.subject, "", authorities)
//            UsernamePasswordAuthenticationToken(principal, authToken, authorities)
//        } else {
//            throw TokenValidFailedException()
//        }
//    }
}