package com.example.demo.dto.rule

import jakarta.validation.constraints.NotBlank

class UpdateRuleDTO(
    @NotBlank(message = "The rule name cannot be blank")
    var description: String,
    @NotBlank(message = "The userId cannot be blank")
    var userId: String,
    var amount: Int = 0,
    var isActive: Boolean = false
)
