package io.jfo.katmash.domain.spi

import io.jfo.katmash.domain.Kat
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface KatDataSource {
    fun getTwoRandomKats(): Flux<Kat>
    fun getTenFirstKatsSortedByScore(): Flux<Kat>
    fun findKatById(id: Int): Mono<Kat>
    fun save(kat: Kat): Mono<Kat>
}
