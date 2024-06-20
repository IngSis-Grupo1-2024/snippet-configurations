package com.example.demo.seed

import com.example.demo.model.*
import com.example.demo.repository.*
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import javax.annotation.processing.Generated

@Component("InitializationConfig")
@Transactional
@Generated
class Seed @Autowired constructor(
    private val configurationRepository: ConfigurationRepository,
    private val ruleDescriptionRepository: RuleDescriptionRepository,
    private val ruleRepository: RuleRepository,
    private val versionRepository: VersionRepository,
    private val ruleTypeRepository: RuleTypeRepository

): CommandLineRunner{

    override fun run(vararg args: String?) {
        seedDb()
    }
    fun seedDb() {
        val formatting = RuleType("FORMATTING")
        val linting = RuleType("LINTING")
        this.ruleTypeRepository.save(formatting)
        this.ruleTypeRepository.save(linting)
        val spaceBeforeAssignation = RuleDescription("SPACE BETWEEN ASSIGNATION")
        val spaceBeforeIdentifier = RuleDescription("SPACE BEFORE DECLARATION")
        val spaceAfterIdentifier = RuleDescription("SPACE AFTER DECLARATION")
        val newLinesAfterPrint = RuleDescription("LINES AFTER PRINT")
        val tabsAfterIf = RuleDescription("TABS AFTER IF")
        val identifierAsArgument = RuleDescription("IDENTIFIER IN FUNCTION")
        val expressionAsArgument = RuleDescription("EXPRESSION IN FUNCTION")
        val literalAsArgument = RuleDescription("LITERAL IN FUNCTION")
        val camelCase = RuleDescription("CAMEL CASE")
        val snakeCase = RuleDescription("SNAKE CASE")
        this.ruleDescriptionRepository.save(spaceBeforeAssignation)
        this.ruleDescriptionRepository.save(spaceBeforeIdentifier)
        this.ruleDescriptionRepository.save(spaceAfterIdentifier)
        this.ruleDescriptionRepository.save(newLinesAfterPrint)
        this.ruleDescriptionRepository.save(tabsAfterIf)
        this.ruleDescriptionRepository.save(identifierAsArgument)
        this.ruleDescriptionRepository.save(expressionAsArgument)
        this.ruleDescriptionRepository.save(literalAsArgument)
        this.ruleDescriptionRepository.save(camelCase)
        this.ruleDescriptionRepository.save(snakeCase)
        val firstVersion = Version("1.0.0")
        val secondVersion = Version("1.1.0")
        this.versionRepository.save(firstVersion)
        this.versionRepository.save(secondVersion)
    }
}
