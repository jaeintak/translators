//package com.translator.validation.constraints
//
//import com.translator.validation.StringEnumCollectionValidator
//import com.translator.validation.StringEnumValidator
//import javax.validation.Constraint
//import javax.validation.Payload
//import kotlin.reflect.KClass
//
//@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
//@Retention(AnnotationRetention.RUNTIME)
//@Constraint(validatedBy = [StringEnumValidator::class, StringEnumCollectionValidator::class])
//annotation class StringEnum(
//    val candidates: Array<String> = [],
//    val message: String = MESSAGE_PREFIX,
//    val groups: Array<KClass<*>> = [],
//    val payload: Array<KClass<out Payload>> = []
//) {
//    companion object {
//        const val MESSAGE_PREFIX = "value(s) have to be one of available values: "
//    }
//}
