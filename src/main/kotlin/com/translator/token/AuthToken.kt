package com.translator.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import java.security.Key
import java.util.*


@Slf4j
@RequiredArgsConstructor
class AuthToken(
    private val token: String,
    private val role: String? = null,
    private val expiry: Date? = null,
    private val key: Key
) {
    private val log = LoggerFactory.getLogger(AuthToken::class.java)

    private fun createAuthToken(id: String, expiry: Date): String {
        return Jwts.builder()
            .setSubject(id)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(expiry)
            .compact()
    }

    private fun createAuthToken(id: String, role: String, expiry: Date): String {
        return Jwts.builder()
            .setSubject(id)
            .claim(AUTHORITIES_KEY, role)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(expiry)
            .compact()
    }

    fun validate(): Boolean {
        return getTokenClaims() != null
    }

    fun getTokenClaims(): Claims? {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: SecurityException) {
            log.info("Invalid JWT signature.")
        } catch (e: MalformedJwtException) {
            log.info("Invalid JWT token.")
        } catch (e: ExpiredJwtException) {
            log.info("Expired JWT token.")
        } catch (e: UnsupportedJwtException) {
            log.info("Unsupported JWT token.")
        } catch (e: IllegalArgumentException) {
            log.info("JWT token compact of handler are invalid.")
        }
        return null
    }

    fun getExpiredTokenClaims(): Claims? {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            log.info("Expired JWT token.")
            return e.claims
        }
        return null
    }

    companion object {
        private const val AUTHORITIES_KEY = "role"
    }
}
