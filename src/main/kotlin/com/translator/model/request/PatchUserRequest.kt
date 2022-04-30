package com.translator.model.request

data class PatchUserRequest(
    val userId: Int,
    val name: String?,
    val email: String?
)