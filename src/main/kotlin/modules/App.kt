package modules

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ConfigurationApplication

fun main(args: Array<String>) {
	runApplication<ConfigurationApplication>(*args)
}