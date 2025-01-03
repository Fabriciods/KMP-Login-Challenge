package com.github.frabriciods.kmp_login_challenge.util

sealed interface Result<out D> {
    data class Success<out D>(val data: D) : Result<D>
    data class Error(val error: BaseError) : Result<Nothing>
}

inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> Result.Error(error)
    }

}

inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
    return when (this) {
        is Result.Success -> {
            block(data)
            this
        }
        is Result.Error -> this
    }
}

inline fun <T>Result<T>.onError(block: (BaseError) -> Unit): Result<T> {
    return when(this){
        is Result.Error -> {
            block(error)
            this
        }
        is Result.Success -> this
    }
}