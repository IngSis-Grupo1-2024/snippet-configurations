package com.example.demo.repository

import com.example.demo.model.Language
import org.springframework.data.jpa.repository.JpaRepository

interface LanguageRepository:JpaRepository<Language, Long> {
    fun findByName(name: String): Language?
}
