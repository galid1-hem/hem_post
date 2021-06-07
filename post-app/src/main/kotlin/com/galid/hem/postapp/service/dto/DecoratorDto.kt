package com.galid.hem.postapp.service.dto

import com.galid.hem.postapp.domain.model.Decorator

data class DecoratorDto(
    val value: String,
    val type: Decorator.DecoratorType
)