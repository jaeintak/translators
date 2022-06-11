package com.translator.configuration

import org.bouncycastle.asn1.crmf.SinglePubInfo.web
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest


@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/", "/login", "/logout", "/error").permitAll()
            .antMatchers("/resources/**").permitAll()
            .antMatchers("/*.js").permitAll()
            .antMatchers("/api/**").authenticated()
            .and()
            .oauth2Login()
                .defaultSuccessUrl("/login/success")
                .failureUrl("/login/failure")
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize-client")
                .authorizationRequestRepository(authorizationRequestRepository())
    }

    @Bean
    fun authorizationRequestRepository(): AuthorizationRequestRepository<OAuth2AuthorizationRequest?>? {
        return HttpSessionOAuth2AuthorizationRequestRepository()
    }

}

