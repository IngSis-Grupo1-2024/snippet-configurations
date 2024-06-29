package com.example.demo.dto.configuration

import jakarta.validation.constraints.NotBlank

class ConfigurationDTO(
    @NotBlank(message = "The version cannot be blank")
    var version: String,
    @NotBlank(message = "The language cannot be blank")
    var language: String
)
