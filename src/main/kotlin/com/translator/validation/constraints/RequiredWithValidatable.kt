//package com.translator.validation.constraints
//
//import com.translator.validation.RequiredWithValidator
//import javax.validation.Constraint
//import javax.validation.Payload
//import kotlin.reflect.KClass
//
//@Target(AnnotationTarget.CLASS)
//@Retention(AnnotationRetention.RUNTIME)
//@Constraint(validatedBy = [RequiredWithValidator::class])
//annotation class RequiredWithValidatable(
//    val message: String = "should be present and not empty if any of the other specified fields are present",
//    val groups: Array<KClass<out Any>> = [],
//    val payload: Array<KClass<out Payload>> = []
//)
