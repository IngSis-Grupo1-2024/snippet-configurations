package com.example.demo.repository
import com.example.demo.model.Configuration
import com.example.demo.model.Language
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfigurationRepository : JpaRepository<Configuration, Long>{
    fun findByUserId(userId: String): List<Configuration>
    fun findByUserIdAndLanguage(userId: String, language: Language): Configuration?
}
