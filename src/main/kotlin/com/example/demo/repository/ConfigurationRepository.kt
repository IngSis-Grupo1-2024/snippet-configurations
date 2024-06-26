package com.example.demo.repository
import com.example.demo.model.Configuration
import com.example.demo.model.Language
import com.example.demo.model.Version
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ConfigurationRepository : JpaRepository<Configuration, Long>{
    fun findByUserId(userId: String): Configuration?
    fun findByUserIdAndVersionAndLanguage(userId: String, version: Version, language: Language): Configuration?
}
