package modules.rule.input

class UpdateRuleInput(
    val id: Int,
    val name: String,
    var value: Int?,
    var isActive: Boolean = false
)
