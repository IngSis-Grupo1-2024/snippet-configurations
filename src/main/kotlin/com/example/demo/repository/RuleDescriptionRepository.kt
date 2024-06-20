package com.example.demo.repository

import com.example.demo.model.RuleDescription
import com.example.demo.model.RuleType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RuleDescriptionRepository: JpaRepository<RuleDescription, Long> {
    fun findByDescription(description: String): RuleDescription?
}
