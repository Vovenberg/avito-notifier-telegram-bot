package com.killprojects.avitonotifier

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AvitoNotifierApplication

fun main(args: Array<String>) {
	runApplication<AvitoNotifierApplication>(*args)
}
