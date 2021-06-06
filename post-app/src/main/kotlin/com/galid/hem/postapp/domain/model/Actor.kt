package com.galid.hem.postapp.domain.model

class Actor(
    val actorId: Long,
    val profileImageUrl: String? = null,
    val relationType: RelationType
) {
    enum class RelationType {
        ME, OTHER
    }
}