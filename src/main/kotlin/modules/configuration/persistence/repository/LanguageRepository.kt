package modules.configuration.persistence.repository

import modules.configuration.persistence.entity.Language
import org.springframework.data.jpa.repository.JpaRepository

interface LanguageRepository:JpaRepository<Language, Long> {
    fun findByName(name: String): Language?
}
