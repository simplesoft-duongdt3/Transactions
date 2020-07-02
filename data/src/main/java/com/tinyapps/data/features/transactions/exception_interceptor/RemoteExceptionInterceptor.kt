package com.tinyapps.data.features.transactions.exception_interceptor

import com.tinyapps.common_jvm.exception.ExceptionInterceptor
import com.tinyapps.common_jvm.exception.Failure
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RemoteExceptionInterceptor : ExceptionInterceptor {
    override fun handleException(exception: Exception): Failure? {
        return when (exception) {
            is UnknownHostException -> Failure.InternetError
            is SocketTimeoutException -> Failure.ConnectionTimeout
            is HttpException -> {
                val errorMsg = if (exception.message() != null) {
                    exception.message()
                } else {
                    ""
                }
                Failure.ApiError(exception.code(), errorMsg)
            }
            else -> null
        }
    }
}