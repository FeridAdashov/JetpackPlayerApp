package com.example.domain.interactors

import com.example.domain.entity.BaseEntity
import com.example.domain.entity.RequestResult

open class BaseInteractor {

    fun <T : BaseEntity> generateResult(entity: T): RequestResult<T> {
        return if (entity.code in 200..202)
            RequestResult.Success(body = entity, code = entity.code)
        else RequestResult.Error(code = entity.code, message = entity.message)
    }
}