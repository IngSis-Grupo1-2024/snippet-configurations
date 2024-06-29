package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "RULE_PARENT")
data class RuleParent (
    @Column
    val name: String,
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    constructor() : this("")
}
