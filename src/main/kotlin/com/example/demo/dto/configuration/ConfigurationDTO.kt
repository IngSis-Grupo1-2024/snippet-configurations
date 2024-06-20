package com.example.demo.dto.configuration

import jakarta.validation.constraints.NotBlank

class ConfigurationDTO(
    @NotBlank(message = "The userId cannot be blank")
    var userId: String,
    @NotBlank(message = "The version cannot be blank")
    var version: String
)
