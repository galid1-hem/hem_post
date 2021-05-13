package com.galid.hem.postapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PostAppApplication

fun main(args: Array<String>) {
	runApplication<PostAppApplication>(*args)
}
