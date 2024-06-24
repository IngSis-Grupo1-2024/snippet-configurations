package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "CONFIGURATION")
data class Configuration(

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    var language: Language,

    @Column(length = 512)
    var userId: String,

    @OneToMany(mappedBy = "configuration")
    var rules: List<Rule> = ArrayList()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null


    constructor() : this( Language(), "")

}
