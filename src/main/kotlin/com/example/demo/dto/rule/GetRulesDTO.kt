package com.example.demo.dto.rule

class GetRulesDTO(
    val id: Long,
    var name:String,
    var isActive:Boolean,
    var value:Int?,
    val parent: String
) {


}
