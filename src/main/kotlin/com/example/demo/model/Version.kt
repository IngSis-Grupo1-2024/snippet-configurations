package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "VERSION")
data class Version(
    @Column(length = 512)
    val number: String = "",

    @OneToMany(mappedBy = "version")
    var configurations: List<Configuration> = ArrayList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    companion object {
        fun fromNumber(number: String): Version {
            return Version(number = number)
        }
    }
}
