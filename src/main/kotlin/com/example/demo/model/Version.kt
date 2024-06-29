package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "VERSION")
data class Version(
    @Column(length = 512)
    val number: String = "",

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    var language: Language,
) {
    constructor() : this("", Language())

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    companion object {
        fun fromNumber(number: String): Version {
            return Version(number = number, Language())
        }
    }
}
