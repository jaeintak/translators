package com.translator.repository

import com.translator.constant.TranslatorConstant
import com.translator.model.request.GetTranslatorRequest
import com.translator.model.request.PatchTranslatorRequest
import com.translator.model.request.PostTranslatorRequest
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.translator.Tables.TRANSLATORS
import org.jooq.translator.enums.TranslatorsStatus
import org.jooq.translator.tables.records.TranslatorsRecord
import org.jooq.types.UInteger
import org.springframework.stereotype.Repository

@Repository
class TranslatorRepository(
    private val dsl: DSLContext
) {
    fun find(params: GetTranslatorRequest): List<TranslatorsRecord> {
        val query = dsl.selectQuery(TRANSLATORS)
        query.addSelect(includeFields())
        query.addConditions(TRANSLATORS.STATUS.eq(TranslatorsStatus.active))

        if (params.name != null) {
            query.addConditions(TRANSLATORS.NAME.eq(params.name))
        }
        if (params.priceMax != null) {
            query.addConditions(TRANSLATORS.PRICE.lessOrEqual(UInteger.valueOf(params.priceMax)))
        }
        if (params.languageFrom != null) {
            query.addConditions(TRANSLATORS.LANGUAGE_FROM.eq(params.languageFrom))
        }
        if (params.languageTo != null) {
            query.addConditions(TRANSLATORS.LANGUAGE_TO.eq(params.languageTo))
        }
        return query.fetchInto(TRANSLATORS)
    }

    fun findById(translatorId: Int): TranslatorsRecord? {
        val query = dsl.selectQuery(TRANSLATORS)
        query.addSelect(includeFields())
        query.addConditions(TRANSLATORS.TRANSLATOR_ID.eq(UInteger.valueOf(translatorId)))
        return query.fetchOneInto(TRANSLATORS)
    }

    fun create(params: PostTranslatorRequest): Int {
        dsl.insertInto(
            TRANSLATORS,
            TRANSLATORS.NAME,
            TRANSLATORS.STATUS,
            TRANSLATORS.PRICE,
            TRANSLATORS.AVAILABLE_TIME_IN_MINUTES,
            TRANSLATORS.LANGUAGE_FROM,
            TRANSLATORS.LANGUAGE_TO
        ).values(
            params.name,
            convertStatus(params.status),
            UInteger.valueOf(params.price),
            UInteger.valueOf(params.availableTimeInMinutes),
            params.languageFrom,
            params.languageTo
        ).execute()
        val translatorId = dsl.lastID()
        return translatorId.toInt()
    }

    fun update(params: PatchTranslatorRequest): Int {
        val query = dsl.updateQuery(TRANSLATORS)
        if(params.name != null){
          query.addValue(TRANSLATORS.NAME, params.name)
        }
        if(params.status != null){
            query.addValue(TRANSLATORS.STATUS, convertStatus(params.status))
        }
        if(params.price != null){
            query.addValue(TRANSLATORS.PRICE, UInteger.valueOf(params.price))
        }
        if(params.availableTimeInMinutes != null){
            query.addValue(TRANSLATORS.AVAILABLE_TIME_IN_MINUTES, UInteger.valueOf(params.availableTimeInMinutes))
        }
        if(params.languageFrom != null){
            query.addValue(TRANSLATORS.LANGUAGE_FROM, params.languageFrom)
        }
        if(params.languageTo != null){
            query.addValue(TRANSLATORS.LANGUAGE_TO, params.languageTo)
        }
        return query.execute()
    }

    private fun includeFields(): Set<Field<*>> = setOf(
        TRANSLATORS.TRANSLATOR_ID,
        TRANSLATORS.NAME,
        TRANSLATORS.STATUS,
        TRANSLATORS.PRICE,
        TRANSLATORS.AVAILABLE_TIME_IN_MINUTES,
        TRANSLATORS.LANGUAGE_FROM,
        TRANSLATORS.LANGUAGE_TO
    )

    private fun convertStatus(status: String?) =
        when (status) {
            TranslatorConstant.TRANSLATOR_STATUS_ACTIVE -> TranslatorsStatus.active
            TranslatorConstant.TRANSLATOR_STATUS_INACTIVE -> TranslatorsStatus.inactive
            TranslatorConstant.TRANSLATOR_STATUS_BUSY -> TranslatorsStatus.busy
            else -> null
        }
}