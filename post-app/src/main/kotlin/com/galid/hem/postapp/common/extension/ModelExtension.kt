package com.galid.hem.postapp.common.extension

import com.galid.hem.postapp.domain.model.Actor
import com.galid.hem.postapp.domain.model.Decorator
import com.galid.hem.postapp.domain.model.MediaId
import com.galid.hem.postapp.service.dto.ActorDto
import com.galid.hem.postapp.service.dto.DecoratorDto
import com.galid.hem.postapp.service.dto.MediaIdDto

fun MediaId.toDto(): MediaIdDto = MediaIdDto(this.id, this.type)

fun Decorator.toDto(): DecoratorDto = DecoratorDto(this.value, this.type)

fun Actor.toDto(): ActorDto = ActorDto(this.actorId, this.profileImageUrl, this.relationType)