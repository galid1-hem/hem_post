package com.galid.hem.postapp.domain.model

class Decorator(
    val value: String,
    val type: DecoratorType,
) {
    enum class DecoratorType {
        TEXT, HASHTAG
    }
}