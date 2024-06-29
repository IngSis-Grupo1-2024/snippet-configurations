package modules.rule.dto

class RuleDTO(
    val id: Long,
    var name:String,
    var isActive:Boolean,
    var value:Int?,
    val parent: String
) {


}
