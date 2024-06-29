package modules.configuration.persistence.entity
import jakarta.persistence.*

@Entity
@Table(name = "LANGUAGE")
data class Language(
    @Column(length = 512, unique = true)
    val name: String = "",

    @OneToMany(mappedBy = "language")
    var configurations: List<Configuration> = ArrayList(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    companion object {
        fun fromNumber(name: String): Language {
            return Language( name)
        }
    }

    constructor() : this( "", emptyList())
}
