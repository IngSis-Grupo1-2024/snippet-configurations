package modules.rule.dto

class RuleDTO(
    val id: Long,
    val name:String,
    val isActive:Boolean,
    val value:Int?,
    val parent: String
)
