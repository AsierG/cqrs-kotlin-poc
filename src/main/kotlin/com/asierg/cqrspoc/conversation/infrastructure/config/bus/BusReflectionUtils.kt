package com.asierg.cqrspoc.conversation.infrastructure.config.bus

import org.springframework.aop.support.AopUtils
import org.springframework.core.GenericTypeResolver

class BusReflectionUtils {

    companion object {
        fun <T> resolveGenericTypeResolverClass(
            objectClass: Any,
            expectedInterface: Class<*>,
            genericIndex: Int,
        ): Class<T>? {
            val targetClass = AopUtils.getTargetClass(objectClass)
            return resolveGenericTypeResolverClass(
                targetClass,
                expectedInterface,
                genericIndex,
            )
        }

        private fun <T> resolveGenericTypeResolverClass(
            targetClass: Class<*>,
            expectedInterface: Class<*>,
            genericIndex: Int,
        ): Class<T>? {
            val arguments = GenericTypeResolver.resolveTypeArguments(targetClass, expectedInterface)
            return if (arguments != null && arguments.size > genericIndex) {
                arguments[genericIndex] as Class<T>
            } else {
                null
            }
        }
    }
}
