package com.translator.controller.builder

import com.translator.model.response.TranslatorResponse
import org.jooq.translator.tables.records.TranslatorsRecord
import org.springframework.stereotype.Component

@Component
class TranslatorResponseBuilder() {

    fun buildTranslatorResponse(translator: TranslatorsRecord): TranslatorResponse =
        TranslatorResponse(
            translator = TranslatorResponse.Translator(
                translatorId = translator.translatorId.toInt(),
                name = "needs fix", //translator.name,
                status = translator.status.toString(),
                price = translator.price.toInt(),
                availableTimeInMinutes = translator.availableTimeInMinutes.toInt(),
                languageFrom = translator.languageFrom.split(","),
                languageTo = translator.languageTo.split(","),
            )
        )

    fun buildTranslatorsResponse(translators: List<TranslatorsRecord>): List<TranslatorResponse> =
        translators.map { translator ->
            TranslatorResponse(
                translator = TranslatorResponse.Translator(
                    translatorId = translator.translatorId.toInt(),
                    name = "needs fix", //translator.name,
                    status = translator.status.toString(),
                    price = translator.price.toInt(),
                    availableTimeInMinutes = translator.availableTimeInMinutes.toInt(),
                    languageFrom = translator.languageFrom.split(","),
                    languageTo = translator.languageTo.split(","),
                )
            )
        }
}