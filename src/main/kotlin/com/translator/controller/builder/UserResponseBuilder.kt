package com.translator.controller.builder

import com.translator.model.response.UserResponse
import org.jooq.translator.tables.records.UsersRecord
import org.jooq.types.UInteger
import org.springframework.stereotype.Component

@Component
class UserResponseBuilder() {

    fun build(user: UsersRecord) = UserResponse(
        user = UserResponse.User(
            userId = user.userId.toInt(),
            name = user.name,
            email = user.email
        )
    )
}