package com.translator.model.response

data class TranslatorResponse(
    val translator: Translator
) {
    data class Translator(
        val translatorId: Int,
        val name: String,
        val status: String,
        val price: Int,
        val availableTimeInMinutes: Int,
        val languageFrom: List<String>,
        val languageTo: List<String>
    )
}