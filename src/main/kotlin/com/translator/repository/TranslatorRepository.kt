package com.translator.repository

import com.translator.constant.TranslatorConstant
import com.translator.model.request.GetTranslatorRequest
import com.translator.model.request.PatchTranslatorRequest
import com.translator.model.request.PostTranslatorRequest
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.JoinType
import org.jooq.translator.Tables.TRANSLATORS
import org.jooq.translator.Tables.USERS
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

        query.addJoin(USERS, USERS.USER_ID.eq(TRANSLATORS.USER_ID))

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
        query.addJoin(USERS, USERS.USER_ID.eq(TRANSLATORS.USER_ID))

        query.addSelect(includeFields())
        query.addConditions(TRANSLATORS.TRANSLATOR_ID.eq(UInteger.valueOf(translatorId)))
        return query.fetchOneInto(TRANSLATORS)
    }

    fun create(request: PostTranslatorRequest): Int {
        dsl.insertInto(
            TRANSLATORS,
            TRANSLATORS.USER_ID,
            TRANSLATORS.STATUS,
            TRANSLATORS.PRICE,
            TRANSLATORS.AVAILABLE_TIME_IN_MINUTES,
            TRANSLATORS.LANGUAGE_FROM,
            TRANSLATORS.LANGUAGE_TO,
            TRANSLATORS.DESCRIPTION,
            TRANSLATORS.INTRODUCTION
        ).values(
            UInteger.valueOf(request.userId),
            convertStatus(request.status),
            UInteger.valueOf(request.price),
            UInteger.valueOf(request.availableTimeInMinutes),
            request.languageFrom.joinToString(","),
            request.languageTo.joinToString(","),
            request.description,
            request.introduction
        ).execute()
        val translatorId = dsl.lastID()
        return translatorId.toInt()
    }

    fun update(request: PatchTranslatorRequest): Int {
        val query = dsl.updateQuery(TRANSLATORS)
        if(request.status != null){
            query.addValue(TRANSLATORS.STATUS, convertStatus(request.status))
        }
        if(request.price != null){
            query.addValue(TRANSLATORS.PRICE, UInteger.valueOf(request.price))
        }
        if(request.availableTimeInMinutes != null){
            query.addValue(TRANSLATORS.AVAILABLE_TIME_IN_MINUTES, UInteger.valueOf(request.availableTimeInMinutes))
        }
        if(request.languageFrom != null){
            query.addValue(TRANSLATORS.LANGUAGE_FROM, request.languageFrom)
        }
        if(request.languageTo != null){
            query.addValue(TRANSLATORS.LANGUAGE_TO, request.languageTo)
        }
        if(request.description != null){
            query.addValue(TRANSLATORS.DESCRIPTION, request.languageTo)
        }
        if(request.introduction != null){
            query.addValue(TRANSLATORS.INTRODUCTION, request.languageTo)
        }
        return query.execute()
    }

    private fun includeFields(): Set<Field<*>> = setOf(
        TRANSLATORS.TRANSLATOR_ID,
        USERS.NAME,
        TRANSLATORS.STATUS,
        TRANSLATORS.PRICE,
        TRANSLATORS.AVAILABLE_TIME_IN_MINUTES,
        TRANSLATORS.LANGUAGE_FROM,
        TRANSLATORS.LANGUAGE_TO,
        TRANSLATORS.DESCRIPTION,
        TRANSLATORS.INTRODUCTION
    )

    private fun convertStatus(status: String?) =
        when (status) {
            TranslatorConstant.TRANSLATOR_STATUS_ACTIVE -> TranslatorsStatus.active
            TranslatorConstant.TRANSLATOR_STATUS_INACTIVE -> TranslatorsStatus.inactive
            TranslatorConstant.TRANSLATOR_STATUS_BUSY -> TranslatorsStatus.busy
            else -> null
        }
}