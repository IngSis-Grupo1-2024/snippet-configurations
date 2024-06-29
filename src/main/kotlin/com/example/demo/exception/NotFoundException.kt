package com.example.demo.exception

data class NotFoundException(override val message: String?) : RuntimeException(message)
