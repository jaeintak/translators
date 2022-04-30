package com.translator.validation

import com.translator.util.extension.isNullOrEmpty
import com.translator.validation.constraints.Required
import com.translator.validation.constraints.RequiredValidatable
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class RequiredValidator : ConstraintValidator<RequiredValidatable, Any> {

    override fun isValid(obj: Any, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()

        val clazz = obj::class.java
        var result = true

        clazz.declaredFields.forEach { field ->
            field.isAccessible = true
            if (field.isAnnotationPresent(Required::class.java)) {
                val value = field.get(obj)
                if (value.isNullOrEmpty()) {
                    result = false
                    context.buildConstraintViolationWithTemplate(context.defaultConstraintMessageTemplate)
                        .addPropertyNode(field.name)
                        .addConstraintViolation()
                }
            }
        }

        return result
    }

}
