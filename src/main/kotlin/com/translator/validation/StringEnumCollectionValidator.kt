package com.translator.validation

import com.translator.validation.constraints.StringEnum
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class StringEnumCollectionValidator : ConstraintValidator<StringEnum, Collection<Any?>> {

    private lateinit var candidates: Array<String>

    override fun initialize(constraintAnnotation: StringEnum) {
        candidates = constraintAnnotation.candidates
    }

    override fun isValid(values: Collection<Any?>?, context: ConstraintValidatorContext): Boolean {
        if (values.isNullOrEmpty()) return true
        if (values.any { it !== null && it !is String }) return false
        if (values.filterNotNull().all { candidates.contains(it) }) return true

        context
            .apply { disableDefaultConstraintViolation() }
            .buildConstraintViolationWithTemplate(
                StringEnum.MESSAGE_PREFIX + candidates.contentToString()
            )
            .addConstraintViolation()
        return false
    }
}
