package com.translator.model.request

data class PostTranslatorRequest(
    val userId: Int,
    val status: String?,
    val price: Int,
    val availableTimeInMinutes: Int,

    @field:StringEnum

    val languageFrom: List<String>,
    val languageTo: List<String>,
    val description: String?,
    val introduction: String?
)