package com.example.demo.dto.rule

import jakarta.validation.constraints.NotBlank

class UpdateRuleDTO(
    val id: Int,
    val name: String,
    var value: Int?,
    var isActive: Boolean = false
)
