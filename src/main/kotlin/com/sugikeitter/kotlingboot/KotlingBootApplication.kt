package com.sugikeitter.kotlingboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlingBootApplication

fun main(args: Array<String>) {
    runApplication<KotlingBootApplication>(*args)
}
