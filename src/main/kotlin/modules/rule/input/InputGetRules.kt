package modules.rule.input

import jakarta.validation.constraints.NotBlank

class InputGetRules(
    @NotBlank(message = "The rule type cannot be blank")
    var ruleType: String
) {
}

