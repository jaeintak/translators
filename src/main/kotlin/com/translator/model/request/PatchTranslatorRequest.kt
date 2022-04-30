package com.translator.model.request

data class PatchTranslatorRequest(
    val translatorId: Int,
    val status: String?,
    val price: Int?,
    val availableTimeInMinutes: Int?,
    val languageFrom: String?,
    val languageTo: String?,
    val description: String?,
    val introduction: String?
)