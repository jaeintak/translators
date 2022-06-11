//package com.translator.lib.validation
//
//import com.translator.util.extension.isNullOrEmpty
//import com.translator.util.extension.toCamelFromSnake
//import com.translator.validation.constraints.RequiredWithValidatable
//import com.translator.validation.constraints.RequiredWith
//import javax.validation.ConstraintValidator
//import javax.validation.ConstraintValidatorContext
//
//class RequiredWithValidator : ConstraintValidator<RequiredWithValidatable, Any> {
//
//    override fun isValid(obj: Any, context: ConstraintValidatorContext): Boolean {
//        context.disableDefaultConstraintViolation()
//
//        val clazz = obj::class.java
//        val annotationMap: Map<String, Annotation?> = clazz.declaredFields.map { prop ->
//            val annotation = prop.declaredAnnotations.find { it is RequiredWith }
//            prop.name to annotation
//        }.toMap()
//
//        var result = true
//        clazz.declaredFields.forEach { prop ->
//            prop.isAccessible = true
//            val annotation = (annotationMap[prop.name] as RequiredWith?) ?: return@forEach
//
//            val fields = annotation.fields.filter { field ->
//                val value = clazz.getDeclaredField(field.toCamelFromSnake())
//                    .apply { isAccessible = true }
//                    .get(obj)
//                !value.isNullOrEmpty()
//            }
//
//            if (fields.isNotEmpty() && prop.get(obj).isNullOrEmpty()) {
//                context.buildConstraintViolationWithTemplate(context.defaultConstraintMessageTemplate)
//                    .addPropertyNode(prop.name)
//                    .addConstraintViolation()
//                result = false
//            }
//        }
//
//        return result
//    }
//
//}
