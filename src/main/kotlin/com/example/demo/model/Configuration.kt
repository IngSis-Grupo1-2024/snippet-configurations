package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(
    name = "CONFIGURATION",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["language_id", "user_id"])
    ])
data class Configuration(

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    var language: Language,

    @Column(length = 512, name = "user_id", nullable = false)
    var userId: String,

    @Column(length = 512, name = "version_id", nullable = false)
    var version: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null


    constructor() : this( Language(), "", "")

}
