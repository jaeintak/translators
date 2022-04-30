package com.translator.validation.constraints

import com.translator.validation.RequiredWithoutValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [RequiredWithoutValidator::class])
annotation class RequiredWithoutValidatable(
    val message: String = "should be present and not empty only when any of the other specified fields are not present",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []
)
