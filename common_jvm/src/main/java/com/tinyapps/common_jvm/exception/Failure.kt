package com.tinyapps.common_jvm.exception

sealed class Failure {
    object InternetError : Failure() {
        override fun toString(): String {
            return "InternetError"
        }
    }
    object ConnectionTimeout : Failure() {
        override fun toString(): String {
            return "ConnectionTimeout"
        }
    }
    object ConnectError: Failure() {
        override fun toString(): String {
            return "ConnectionError"
        }
    }
    object NetworkError : Failure() {
        override fun toString(): String {
            return "NetworkError"
        }
    }
    data class ApiError(val httpCode: Int, val errorMsg: String) : Failure() {
        override fun toString(): String {
            return "ApiError httpCode : $httpCode errorMsg : $errorMsg"
        }
    }

    data class InternalError(val errorMsg: String) : Failure() {
        override fun toString(): String {
            return "InternalError errorMsg : $errorMsg"
        }
    }

    data class MaintainenanceError(val errorCode: Int, val errorMsg: String) : Failure() {
        override fun toString(): String {
            return " MaintainenanceError errorCode : $errorCode errorMsg : $errorMsg"
        }
    }

    data class ServerError(val errorCode: Int, val errorMsg: String) : Failure() {
        override fun toString(): String {
            return " ServerError errorCode : $errorCode errorMsg : $errorMsg"
        }
    }

    class UnCatchError(val exception: Exception) : Failure() {
        override fun toString(): String {
            return " UnCatchError exception : $exception"
        }
    }

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()

    override fun toString(): String {
        return super.toString()
    }
}