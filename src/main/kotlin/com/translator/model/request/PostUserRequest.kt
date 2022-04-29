package com.translator.model.request

data class PostUserRequest(
    val name: String,
    val status: String?,
    val price: Int,
    val availableTimeInMinutes: Int,
    val languageFrom: String,
    val languageTo: String
)