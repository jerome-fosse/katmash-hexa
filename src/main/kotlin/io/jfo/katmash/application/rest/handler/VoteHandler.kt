package io.jfo.katmash.application.rest.handler

import io.jfo.katmash.domain.exception.KatNotFoundException
import io.jfo.katmash.application.rest.RestAdapter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono

@Component
class VoteHandler(val katAdapter: RestAdapter): RequestHandler {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(VoteHandler::class.java)
    }

    override fun handleRequest(request: ServerRequest): Mono<ServerResponse> {
        val katId = request.pathVariable("id").toInt()
        logger.info("New vote for Kat $katId received !!!")

        return katAdapter.voteForKatWithId(katId)
                .then(noContent().build())
                .doOnError { KatPairHandler.logger.error(it.message, it) }
                .onErrorResume {
                    when (it) {
                        is KatNotFoundException -> status(HttpStatus.NOT_FOUND).body(BodyInserters.fromObject(it.message ?: "Kat not found"))
                        else -> status(HttpStatus.INTERNAL_SERVER_ERROR).body(BodyInserters.fromObject(it.message ?: "Unexpected Error."))
                    }
                }
    }
}
