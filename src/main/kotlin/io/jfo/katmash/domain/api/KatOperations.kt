package io.jfo.katmash.domain.api

import io.jfo.katmash.domain.Kat
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface KatOperations {

    fun getRanking(): Flux<Kat>

    fun getRandomPairOfKats(): Flux<Kat>

    fun voteForKatWithId(id: Int): Mono<Kat>
}
