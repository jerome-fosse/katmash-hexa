package io.jfo.katmash.application.rest.handler

import io.jfo.katmash.application.rest.RestAdapter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class RankingHandler(val katAdapter: RestAdapter): RequestHandler {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(RankingHandler::class.java)
    }

    override fun handleRequest(request: ServerRequest): Mono<ServerResponse> {
        logger.info("Requesting the ranking...")

        return katAdapter.getRanking()
                .collectList()
                .flatMap {
                    logger.debug("The ranking is : $it")
                    ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromObject(it))
                }
                .doOnError { KatPairHandler.logger.error(it.message, it)}
                .onErrorResume { ServerResponse.badRequest().body(BodyInserters.fromObject("Something went wrong !!!")) }
    }
}
