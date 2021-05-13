package com.galid.hem.postapp.common.extension

import org.bson.types.ObjectId

fun String.toObjectId(): ObjectId = ObjectId(this)