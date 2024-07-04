package modules.rule.input

class UpdateRuleInput(
    val id: Int,
    val name: String,
    val value: Int?,
    val isActive: Boolean = false
)
