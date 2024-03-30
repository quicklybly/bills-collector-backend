package com.quicklybly.billscollector

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BillsCollectorApplication

fun main(args: Array<String>) {
	runApplication<BillsCollectorApplication>(*args)
}
