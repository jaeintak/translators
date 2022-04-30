package com.translator.lib.validation

import com.translator.validation.constraints.CsvIn
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class CsvInValidator : ConstraintValidator<CsvIn, String> {

    private lateinit var candidates: Array<String>

    override fun initialize(constraintAnnotation: CsvIn) {
        candidates = constraintAnnotation.candidates
    }

    override fun isValid(csv: String?, context: ConstraintValidatorContext): Boolean {
        if (csv.isNullOrEmpty()) {
            return true
        }

        for (value in csv.split(",")) {
            var validValue = false
            for (candidate in candidates) {
                if (value == candidate) {
                    validValue = true
                    break
                }
            }

            if (!validValue) {
                context
                    .apply { disableDefaultConstraintViolation() }
                    .buildConstraintViolationWithTemplate(
                        CsvIn.MESSAGE_PREFIX + candidates.contentToString()
                    )
                    .addConstraintViolation()
                return false
            }
        }

        return true
    }
}
