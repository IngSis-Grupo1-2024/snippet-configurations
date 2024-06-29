package com.example.demo.dto.rule

import jakarta.validation.constraints.NotBlank

class InputGetRulesDto(
    @NotBlank(message = "The rule type cannot be blank")
    var ruleType: String
) {
}

