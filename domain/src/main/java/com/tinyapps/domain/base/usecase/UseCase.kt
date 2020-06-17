package com.tinyapps.domain.base.usecase

import com.tinyapps.common_jvm.exception.Failure
import com.tinyapps.common_jvm.functional.Either

abstract class UseCase<Params : UseCaseParams, Result>() {

    suspend fun execute(params: Params): Either<Failure, Result> {
        return executeInternal(params)
    }

    protected abstract suspend fun executeInternal(params: Params): Either<Failure, Result>
}