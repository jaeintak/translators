package com.translator.api

import com.translator.controller.builder.UserResponseBuilder
import com.translator.model.request.GetUserRequest
import com.translator.model.request.PatchUserRequest
import com.translator.model.request.PostUserRequest
import com.translator.model.response.UserResponse
import com.translator.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RestUserController(
    private val service: UserService,
    private val builder: UserResponseBuilder
) {
    @GetMapping("/api/user")
    fun get(@RequestParam userId: Int): UserResponse? { // snake to camel
        val user = requireNotNull(service.getById(userId)){
            "user ($userId) is not found"
        }
        return builder.build(user)
    }

    @PostMapping("/api/user")
    fun post(@RequestBody request: PostUserRequest): UserResponse? {
        val userId = service.post(request)
        val user = requireNotNull(service.getById(userId)){
            "user ($userId) is not found"
        }
        return builder.build(user)
    }

//    @PatchMapping("/user") what is there to patch for users?
//    fun patch(@RequestBody request: PatchUserRequest): UserResponse {
//        val userId = service.patch(request)
//        return builder.build(service.getById(userId))
//    }
}