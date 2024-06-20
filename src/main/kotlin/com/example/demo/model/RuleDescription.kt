package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "RULE_DESCRIPTION")
data class RuleDescription(

    @Column
    val description: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    constructor() : this("")
}

