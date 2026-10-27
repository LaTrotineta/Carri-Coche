package com.github.La_Trotineta.Carri_Coche

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class CarriCocheApplication

fun main(args: Array<String>) {
	runApplication<CarriCocheApplication>(*args)
}
