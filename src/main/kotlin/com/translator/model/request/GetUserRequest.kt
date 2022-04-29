package com.translator.model.request

data class GetUserRequest(
    val name: String?,
    val priceMax: Int?,
    val languageFrom: String?,
    val languageTo: String?
)