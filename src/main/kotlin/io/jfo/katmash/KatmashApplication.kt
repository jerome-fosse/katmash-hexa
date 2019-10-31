package io.jfo.katmash

import io.jfo.katmash.domain.KatService
import io.jfo.katmash.domain.spi.KatDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class KatmashApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<KatmashApplication>(*args)
        }
    }

    @Bean
    fun katRequest(katDataSource: KatDataSource) = KatService(katDataSource)
}
