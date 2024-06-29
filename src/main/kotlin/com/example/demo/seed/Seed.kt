package com.example.demo.seed

import com.example.demo.model.Language
import com.example.demo.model.*
import com.example.demo.repository.*
import com.example.demo.testCase.model.entity.VariableType
import com.example.demo.testCase.repository.VariableTypeRepository
import jakarta.transaction.Transactional
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
    private val variableTypeRepository: VariableTypeRepository,
    private val ruleParentRepository: RuleParentRepository,
): CommandLineRunner{

    override fun run(vararg args: String?) {
        seedDb()
    }
    fun seedDb() {
        val (formatting, linting) = saveRuleType()
        val print = RuleParent("println")
        val identifierFormat = RuleParent("identifier_format")
        this.ruleParentRepository.save(print)
        this.ruleParentRepository.save(identifierFormat)
        val readInput = RuleParent("readinput")
        val emptyParent = RuleParent("")
        this.ruleParentRepository.save(readInput)
        this.ruleParentRepository.save(emptyParent)
//        Format
        val spaceBeforeAssignation = RuleDescription("SPACES BETWEEN ASSIGNATION", formatting, emptyParent)
        val spaceBeforeIdentifier = RuleDescription("SPACES BEFORE DECLARATION", formatting, emptyParent)
        val spaceAfterIdentifier = RuleDescription("SPACES AFTER DECLARATION",formatting, emptyParent)
        val newLinesAfterPrint = RuleDescription("LINES AFTER PRINT", formatting, emptyParent)
        val tabsAfterIf = RuleDescription("TABS AFTER IF", formatting, emptyParent)
//        sca
        val expPrint = RuleDescription("expression", linting, print)
        val identifierPrint = RuleDescription("identifier", linting, print)
        val literalPrint = RuleDescription("literal", linting, print)
        val expReadInput = RuleDescription("expression", linting, readInput)
        val identifierReadInput = RuleDescription("identifier", linting, readInput)
        val literalReadInput = RuleDescription("literal",linting, readInput)
        val snakeCase = RuleDescription("snake_case", linting, identifierFormat)

        this.ruleDescriptionRepository.save(spaceBeforeAssignation)
        this.ruleDescriptionRepository.save(spaceBeforeIdentifier)
        this.ruleDescriptionRepository.save(spaceAfterIdentifier)
        this.ruleDescriptionRepository.save(newLinesAfterPrint)
        this.ruleDescriptionRepository.save(tabsAfterIf)
        this.ruleDescriptionRepository.save(expPrint)
        this.ruleDescriptionRepository.save(identifierPrint)
        this.ruleDescriptionRepository.save(literalPrint)
        this.ruleDescriptionRepository.save(expReadInput)
        this.ruleDescriptionRepository.save(identifierReadInput)
        this.ruleDescriptionRepository.save(literalReadInput)
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

    private fun saveRuleType(): Pair<RuleType, RuleType> {
        val formatting = RuleType("FORMATTING")
        val linting = RuleType("LINTING")
        this.ruleTypeRepository.save(formatting)
        this.ruleTypeRepository.save(linting)
        return Pair(formatting, linting)
    }
}


