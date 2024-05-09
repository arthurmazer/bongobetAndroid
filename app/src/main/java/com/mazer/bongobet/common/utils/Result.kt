package com.mazer.bongobet.common.utils

sealed class Result<T>(val data: T? = null, val message: String? = null){

    class Sucess<T>(data: T?) : Result<T>(data)

    class Error<T>(
        message: String? = null,
        data: T? = null,
        val throwable: Throwable? = null
    ): Result<T>(data,message)

    override fun toString(): String{
        return "Result(data=$data, message=$message"
    }

}
