package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "RULE_DESCRIPTION")
data class RuleDescription(
    @Column
    val description: String,

    @ManyToOne
    @JoinColumn(name = "rule_type_id", nullable = false)
    val ruleType: RuleType,

    @ManyToOne
    @JoinColumn(name = "parent_id")
    val ruleParent: RuleParent,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    constructor() : this("", RuleType(), RuleParent())
}

