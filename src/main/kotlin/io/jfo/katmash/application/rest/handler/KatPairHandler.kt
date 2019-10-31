package io.jfo.katmash.application.rest.handler

import io.jfo.katmash.application.rest.RestAdapter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono

@Component
class KatPairHandler(val katAdapter: RestAdapter): RequestHandler {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(KatPairHandler::class.java)
    }

    override fun handleRequest(request: ServerRequest): Mono<ServerResponse> {
        logger.info("Requesting a pair of kats...")

        return katAdapter.getRandomPairOfKats()
                .collectList()
                .flatMap {
                    logger.debug("Pair of kats returned : $it")
                    ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromObject(it))
                }
                .doOnError {logger.error(it.message, it)}
                .onErrorResume { badRequest().body(BodyInserters.fromObject("Something went wrong !!!")) }
    }
}
