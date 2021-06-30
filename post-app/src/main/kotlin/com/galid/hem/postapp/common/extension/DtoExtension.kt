package com.galid.hem.postapp.common.extension

import com.galid.hem.postapp.domain.model.Actor
import com.galid.hem.postapp.domain.model.Decorator
import com.galid.hem.postapp.domain.model.MediaId
import com.galid.hem.postapp.service.dto.ActorDto
import com.galid.hem.postapp.service.dto.DecoratorDto
import com.galid.hem.postapp.service.dto.MediaIdDto

fun MediaIdDto.fromDto(): MediaId = MediaId(this.id, this.type, this.imageUrl)

fun DecoratorDto.fromDto(): Decorator = Decorator(this.value, this.type)

fun ActorDto.fromDto(): Actor = Actor(this.actorId, this.profileImageUrl, this.relationType)