//package com.translator.lib.validation
//
//import com.translator.util.extension.isNullOrEmpty
//import com.translator.util.extension.toCamelFromSnake
//import com.translator.validation.constraints.RequiredWithout
//import com.translator.validation.constraints.RequiredWithoutValidatable
//import javax.validation.ConstraintValidator
//import javax.validation.ConstraintValidatorContext
//
//class RequiredWithoutValidator : ConstraintValidator<RequiredWithoutValidatable, Any> {
//
//    override fun isValid(obj: Any, context: ConstraintValidatorContext): Boolean {
//        context.disableDefaultConstraintViolation()
//
//        val clazz = obj::class.java
//        val annotationMap: Map<String, Annotation?> = clazz.declaredFields.associate { prop ->
//            val annotation = prop.declaredAnnotations.find { it is RequiredWithout }
//            prop.name to annotation
//        }
//
//        var result = true
//        clazz.declaredFields.forEach { prop ->
//            prop.isAccessible = true
//            val annotation = (annotationMap[prop.name] as RequiredWithout?) ?: return@forEach
//
//            val fields = annotation.fields.filter { field ->
//                val value = clazz.getDeclaredField(field.toCamelFromSnake())
//                    .apply { isAccessible = true }
//                    .get(obj)
//                !value.isNullOrEmpty()
//            }
//
//            if (fields.isEmpty() && prop.get(obj).isNullOrEmpty()) {
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
