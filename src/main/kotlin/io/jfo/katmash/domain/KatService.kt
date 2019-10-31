package io.jfo.katmash.domain

import io.jfo.katmash.domain.api.KatOperations
import io.jfo.katmash.domain.exception.KatNotFoundException
import io.jfo.katmash.domain.spi.KatDataSource
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class KatService(val katDataSource: KatDataSource): KatOperations {
    override fun getRanking(): Flux<Kat> {
        return katDataSource.getTenFirstKatsSortedByScore()
    }

    override fun getRandomPairOfKats(): Flux<Kat> {
        return katDataSource.getTwoRandomKats()
    }

    override fun voteForKatWithId(id: Int): Mono<Kat> {
        return katDataSource.findKatById(id)
                .switchIfEmpty(Mono.error(KatNotFoundException(id)))
                .flatMap { katDataSource.save(it.copy(score = it.score + 1)) }
    }
}
