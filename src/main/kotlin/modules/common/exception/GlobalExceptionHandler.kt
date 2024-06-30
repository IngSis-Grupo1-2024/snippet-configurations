package modules.common.exception

import modules.rule.controller.RuleController
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(NotFoundException::class)
    fun handleStudentNotFoundException(exception: NotFoundException): ResponseEntity<Any> {
        log.warn("Not found exception, error message: $exception")
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.message)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(exception.message)
    }
}
