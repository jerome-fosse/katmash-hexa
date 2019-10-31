package io.jfo.katmash.application.rest.handler

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface RequestHandler {

    fun handleRequest(request: ServerRequest): Mono<ServerResponse>
}
