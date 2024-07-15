package com.interenting

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InterentingApplication

fun main(args: Array<String>) {
	runApplication<InterentingApplication>(*args)
}
