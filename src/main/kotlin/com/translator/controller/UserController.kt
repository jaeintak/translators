package com.translator.controller

import com.translator.controller.builder.UserResponseBuilder
import com.translator.model.request.GetUserRequest
import com.translator.model.request.PatchUserRequest
import com.translator.model.request.PostUserRequest
import com.translator.model.response.UserResponse
import com.translator.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val service: UserService,
    private val builder: UserResponseBuilder
) {
    @GetMapping("/user")
    fun get(params: GetUserRequest): UserResponse {
        return builder.build(service.get(params))
    }

    @PostMapping("/user")
    fun post(params: PostUserRequest): UserResponse {
        val userId = service.post(params)
        return builder.build(service.getById(userId))
    }

    @PatchMapping("/user")
    fun patch(params: PatchUserRequest): UserResponse {
        val userId = service.patch(params)
        return builder.build(service.getById(userId))
    }
}