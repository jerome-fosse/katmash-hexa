package io.jfo.katmash.application.rest

import io.jfo.katmash.application.rest.model.KatResponse
import io.jfo.katmash.domain.api.KatOperations
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class RestAdapter(val katOperations: KatOperations) {

    fun getRanking(): Flux<KatResponse> =
            katOperations.getRanking()
                    .map { KatResponse(id = it.id, url = it.url, score = it.score) }

    fun getRandomPairOfKats(): Flux<KatResponse> =
            katOperations.getRandomPairOfKats()
                    .map { KatResponse(id = it.id, url = it.url, score = it.score) }

    fun voteForKatWithId(id: Int): Mono<KatResponse> =
            katOperations.voteForKatWithId(id)
                    .map { KatResponse(id = it.id, url = it.url, score = it.score) }
}
