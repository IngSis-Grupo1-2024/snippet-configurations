package com.example.demo.model
import jakarta.persistence.*

@Entity
@Table(name = "LANGUAGE")
data class Language(
    @Column(length = 512)
    val name: String = "",

    @OneToMany(mappedBy = "language")
    var configurations: List<Configuration> = ArrayList(),

    @OneToMany(mappedBy="version")
    var versions: List<Version> = ArrayList()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    companion object {
        fun fromNumber(name: String): Language {
            return Language(name = name)
        }
    }
}
