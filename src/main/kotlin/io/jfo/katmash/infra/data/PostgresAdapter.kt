package io.jfo.katmash.infra.data

import io.jfo.katmash.domain.Kat
import io.jfo.katmash.domain.spi.KatDataSource
import io.jfo.katmash.infra.data.entity.KatEntity
import io.jfo.katmash.infra.data.repository.KatEntityRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class PostgresAdapter(val repository: KatEntityRepository): KatDataSource {
    override fun getTwoRandomKats(): Flux<Kat> =
        repository.findTwoRandomKats()
            .map { Kat(id = it.id, url = it.url, score = it.score) }

    override fun getTenFirstKatsSortedByScore(): Flux<Kat> =
        repository.findKatOrderByScoreDescLimitBy(10)
            .map { Kat(id = it.id, url = it.url, score = it.score) }

    override fun findKatById(id: Int): Mono<Kat> =
        repository.findById(id)
            .map { Kat(id = it.id, url = it.url, score = it.score) }

    override fun save(kat: Kat): Mono<Kat> =
        repository.save(KatEntity(id = kat.id, url = kat.url, score = kat.score))
            .map { Kat(id = it.id, url = it.url, score = it.score) }
}
