package com.example.demo.seed

import com.example.demo.model.Language
import com.example.demo.model.*
import com.example.demo.repository.*
import com.example.demo.testCase.model.entity.VariableType
import com.example.demo.testCase.repository.VariableTypeRepository
import jakarta.transaction.Transactional
import org.aspectj.weaver.VersionedDataInputStream
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import javax.annotation.processing.Generated

@Component("InitializationConfig")
@Transactional
@Generated
class Seed @Autowired constructor(
    private val ruleDescriptionRepository: RuleDescriptionRepository,
    private val languageRepository: LanguageRepository,
    private val ruleTypeRepository: RuleTypeRepository,
    private val variableTypeRepository: VariableTypeRepository

): CommandLineRunner{

    override fun run(vararg args: String?) {
        seedDb()
    }
    fun seedDb() {
        val formatting = RuleType("FORMATTING")
        val linting = RuleType("LINTING")
        this.ruleTypeRepository.save(formatting)
        this.ruleTypeRepository.save(linting)
        val spaceBeforeAssignation = RuleDescription("SPACES BETWEEN ASSIGNATION")
        val spaceBeforeIdentifier = RuleDescription("SPACES BEFORE DECLARATION")
        val spaceAfterIdentifier = RuleDescription("SPACES AFTER DECLARATION")
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
        val language = Language("PRINTSCRIPT")
        this.languageRepository.save(language)
        val env = VariableType("ENVIRONMENT")
        val input = VariableType("INPUT")
        val output = VariableType("OUTPUT")
        this.variableTypeRepository.save(env)
        this.variableTypeRepository.save(input)
        this.variableTypeRepository.save(output)
    }
}


