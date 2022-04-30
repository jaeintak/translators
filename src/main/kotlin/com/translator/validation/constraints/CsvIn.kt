package com.translator.validation.constraints

import com.translator.validation.CsvInValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CsvInValidator::class])
annotation class CsvIn(
    val candidates: Array<String> = [],
    val message: String = MESSAGE_PREFIX,
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []
) {
    companion object {
        const val MESSAGE_PREFIX = "all data in csv should be each of available values: "
    }
}

