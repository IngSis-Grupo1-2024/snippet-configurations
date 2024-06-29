package modules.rule.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "RULE_TYPE")
data class RuleType(
    @Column(length = 512)
    val type: String,//This is to know if it is for linting or for formatting
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0
    constructor() : this("")
}
