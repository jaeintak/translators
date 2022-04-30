package com.translator.service

import com.translator.model.request.GetTranslatorRequest
import com.translator.model.request.PatchTranslatorRequest
import com.translator.model.request.PostTranslatorRequest
import com.translator.repository.TranslatorRepository
import org.jooq.translator.tables.records.TranslatorsRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TranslatorService(
    private val repository: TranslatorRepository
) {

    fun get(params: GetTranslatorRequest): List<TranslatorsRecord>{
        return repository.find(params)
    }

    fun getById(translatorId: Int): TranslatorsRecord? {
        return repository.findById(translatorId)
    }

    @Transactional
    fun post(request: PostTranslatorRequest): Int{
        return repository.create(request)
    }

    @Transactional
    fun patch(request: PatchTranslatorRequest): Int{
        return repository.update(request)
    }
}