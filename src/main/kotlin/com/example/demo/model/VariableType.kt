package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "VARIABLE_TYPE")
data class VariableType(
    val name: String = "",

    @OneToMany(mappedBy = "variableType")
    var variables: List<Variable> = ArrayList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    constructor() : this("")
}
