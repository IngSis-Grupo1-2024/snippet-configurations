package modules.rule.input

import jakarta.validation.constraints.NotBlank

class InputGetRules(
    @NotBlank(message = "The rule type cannot be blank")
    val ruleType: String
) {
}

