package com.example.login.util

sealed class Resource<T>(
    val data: T? = null,
    val nextPage: String? = null,
    val message: String? = null
) {
    class Success<T>(data: T?, nextPage: String? = null): Resource<T>(data, nextPage)
    class Error<T>(message: String): Resource<T>(message = message)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>()
}