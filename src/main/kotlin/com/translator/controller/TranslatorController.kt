package com.translator.controller

import com.translator.controller.builder.TranslatorResponseBuilder
import com.translator.model.request.GetTranslatorRequest
import com.translator.model.request.PatchTranslatorRequest
import com.translator.model.request.PostTranslatorRequest
import com.translator.model.response.TranslatorResponse
import com.translator.service.TranslatorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TranslatorController(
    private val service: TranslatorService,
    private val builder: TranslatorResponseBuilder
) {
    @GetMapping("/translator")
    fun get(params: GetTranslatorRequest): List<TranslatorResponse> { // some kind of validation is needed
        return builder.buildTranslatorsResponse(service.get(params))
    }

    @PostMapping("/translator")
    fun post(@RequestBody request: PostTranslatorRequest): TranslatorResponse {  //camel to snake
        val translatorId = service.post(request)
        val translator = requireNotNull(service.getById(translatorId)){
            "translator ($translatorId) is not found"
        }
        return builder.buildTranslatorResponse(translator)
    }

    @PatchMapping("/translator")
    fun patch(params: PatchTranslatorRequest): TranslatorResponse? {
        service.patch(params)
        return service.getById(params.translatorId)?.let {
            builder.buildTranslatorResponse(it)
        }
    }
}