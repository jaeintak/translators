package com.translator.model.request

data class GetTranslatorRequest(
    val name: String?,
    val priceMax: Int?,
    val languageFrom: String?,
    val languageTo: String?
)