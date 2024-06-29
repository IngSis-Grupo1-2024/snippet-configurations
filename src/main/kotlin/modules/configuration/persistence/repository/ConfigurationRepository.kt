package modules.configuration.persistence.repository
import modules.configuration.persistence.entity.Configuration
import modules.configuration.persistence.entity.Language
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfigurationRepository : JpaRepository<Configuration, Long>{
    fun findByUserId(userId: String): List<Configuration>
    fun findByUserIdAndLanguage(userId: String, language: Language): Configuration?
}
