package com.galid.hem.postapp.presenter

data class Response<T>(
    val data: T? = null,
    val status: Int? = 200,
    val message: String? = "OK"
)