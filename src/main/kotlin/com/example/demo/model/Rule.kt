package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "RULE")
data class Rule(

    @ManyToOne
    @JoinColumn(name = "rule_type_id", nullable = false)
    var ruleType: RuleType,

    @ManyToOne
    @JoinColumn(name = "configuration_id", nullable = false)
    var configuration: Configuration,

    @ManyToOne
    @JoinColumn(name = "rule_description_id", nullable = false)
    var ruleDescription: RuleDescription,

    @Column
    var amount: Int,

    @Column
    var isActive: Boolean = false
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null


    constructor() : this(RuleType(), Configuration(),RuleDescription(), 0)
}
