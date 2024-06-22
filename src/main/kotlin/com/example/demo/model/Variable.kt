package com.example.demo.model

import jakarta.persistence.*


@Entity
@Table(name = "VARIABLES")
data class Variable(

    @Column(length = 512)
    val snippetId: String = "",

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "VARIABLES")
    @Column(name = "variables")
    var variables: List<String> = listOf(),

    @ManyToOne
    @JoinColumn(name = "variable_type_id", nullable = false)
    var variableType: VariableType,


) {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0

        constructor() : this("",listOf(), VariableType())
}
