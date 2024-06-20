package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "CONFIGURATION")
data class Configuration(

    @ManyToOne
    @JoinColumn(name = "version_number", nullable = false)
    var version: Version,

    @Column(length = 512, unique = true)
    var userId: String,

    @OneToMany(mappedBy = "configuration")
    var rules: List<Rule> = ArrayList()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null


    constructor() : this(Version(), "")

}
