package modules.common.exception

data class NotFoundException(override val message: String?) : RuntimeException(message)
