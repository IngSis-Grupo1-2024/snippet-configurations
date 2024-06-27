package com.example.demo.dto.rule

class UpdateRuleDTO(
    val id: Int,
    val name: String,
    var value: Int?,
    var isActive: Boolean = false
)
