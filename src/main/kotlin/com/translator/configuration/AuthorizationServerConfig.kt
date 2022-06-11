package com.translator.configuration

import com.translator.service.AuthService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig(
    @Value("\${example.jwt-secret}")
    private val jwtSecret: String,
    private val authService: AuthService
): AuthorizationServerConfigurerAdapter() {

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("permitAll()")
            .allowFormAuthenticationForClients()
            .passwordEncoder(NoOpPasswordEncoder.getInstance())
            .addTokenEndpointAuthenticationFilter(CorsFilter(corsConfigurationSource()))
    }

    @Bean
    fun corsConfigurationSource() = CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.addAllowedOrigin("*")
        corsConfiguration.addAllowedHeader("*")
        corsConfiguration.addAllowedMethod("*")
        corsConfiguration
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient("clientId")
            .secret("clientSecret")
            .scopes("read", "write")
            .authorizedGrantTypes("client_credentials", "password", "authorization_code", "refresh_token")
            .accessTokenValiditySeconds(1800)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
            .userDetailsService(authService)
            .accessTokenConverter(defaultAccessTokenConverter)
            .tokenStore(JwtTokenStore(jwtAccessTokenConverter))
            .authenticationManager(authenticationManager)
            .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
            .tokenEnhancer(jwtAccessTokenConverter)
    }

    val authenticationManager = AuthenticationManager { authentication ->
        val input = authentication as UsernamePasswordAuthenticationToken

        val user = authService.login()

        UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

    val defaultAccessTokenConverter = object : DefaultAccessTokenConverter() {}

    val jwtAccessTokenConverter = JwtAccessTokenConverter().also {
        it.setSigningKey(jwtSecret)
        it.accessTokenConverter = defaultAccessTokenConverter
        it.afterPropertiesSet()
    }
}