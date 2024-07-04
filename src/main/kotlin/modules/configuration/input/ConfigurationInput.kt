package modules.configuration.input

import jakarta.validation.constraints.NotBlank

class ConfigurationInput(
    @NotBlank(message = "The version cannot be blank")
    val version: String,
    @NotBlank(message = "The language cannot be blank")
    val language: String
)
