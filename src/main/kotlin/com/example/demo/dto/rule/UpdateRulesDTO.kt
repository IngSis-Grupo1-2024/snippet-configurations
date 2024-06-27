package com.example.demo.dto.rule

import jakarta.validation.constraints.NotBlank

class UpdateRulesDTO(
    val rules: List<UpdateRuleDTO>
)
