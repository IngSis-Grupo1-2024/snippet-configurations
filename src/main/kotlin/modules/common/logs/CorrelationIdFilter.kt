package org.austral.snippet.permission.server

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*

@Component
class CorrelationIdFilter : WebFilter {

    companion object {
        private const val CORRELATION_ID_KEY = "correlation-id"
        private const val CORRELATION_ID_HEADER = "X-Correlation-Id"
    }

    private val logger = LoggerFactory.getLogger(CorrelationIdFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val correlationId: String = exchange.request.headers[CORRELATION_ID_HEADER]?.firstOrNull() ?: UUID.randomUUID().toString()
        logger.info("Correlation id: $correlationId")
        MDC.put(CORRELATION_ID_KEY, correlationId)
        try {
            return chain.filter(exchange)
        } finally {
            MDC.remove(CORRELATION_ID_KEY)
        }
    }
}