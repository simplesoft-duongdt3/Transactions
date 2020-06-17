package com.tinyapps.common_jvm.exception

interface ExceptionInterceptor {
    fun handleException(exception: Exception): Failure?
}