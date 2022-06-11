//package com.translator.validation
//
//import com.translator.validation.constraints.StringEnum
//import javax.validation.ConstraintValidator
//import javax.validation.ConstraintValidatorContext
//
//class StringEnumValidator : ConstraintValidator<StringEnum, String> {
//
//    private lateinit var candidates: Array<String>
//
//    override fun initialize(constraintAnnotation: StringEnum) {
//        candidates = constraintAnnotation.candidates
//    }
//
//    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
//        if (value == null) {
//            return true
//        }
//
//        if (candidates.contains(value)) return true
//
//        context
//            .apply { disableDefaultConstraintViolation() }
//            .buildConstraintViolationWithTemplate(
//                StringEnum.MESSAGE_PREFIX + candidates.contentToString()
//            )
//            .addConstraintViolation()
//        return false
//    }
//}