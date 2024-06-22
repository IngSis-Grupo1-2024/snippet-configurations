package com.example.demo.service

import com.example.demo.dto.configuration.ConfigurationDTO
import com.example.demo.dto.output.RulesDto
import com.example.demo.dto.rule.GetRulesDTO
import com.example.demo.dto.rule.InputGetRulesDto
import com.example.demo.dto.rule.UpdateRuleDTO
import com.example.demo.model.*
import com.example.demo.repository.*
import org.springframework.stereotype.Service

@Service
class ConfigurationService(
    private val configurationRepository: ConfigurationRepository,
    private val versionRepository: VersionRepository,
    private val ruleRepository: RuleRepository,
    private val ruleTypeRepository: RuleTypeRepository,
    private val ruleDescriptionRepository: RuleDescriptionRepository
) {

    fun createConfiguration(configurationDTO: ConfigurationDTO) {
        val versionObject = this.versionRepository.findByNumber(configurationDTO.version)!!
        val configuration = this.configurationRepository.save(Configuration(versionObject, configurationDTO.userId))
        seedRules(configuration)

    }

    fun getRulesByType(inputGetRulesDto: InputGetRulesDto): RulesDto {
        val ruleType = this.ruleTypeRepository.findByType(inputGetRulesDto.ruleType)!!
        val configuration = this.configurationRepository.findByUserId(inputGetRulesDto.userId)!!
        val rules: List<Rule> = this.ruleRepository.findByRuleTypeAndConfiguration(ruleType, configuration)
        return RulesDto(rules.map { rule ->
           GetRulesDTO(rule.ruleDescription.description, rule.isActive, rule.amount.toString())
        })
    }

    fun updateVersion(configurationDTO: ConfigurationDTO) {
        val configuration = this.configurationRepository.findByUserId(configurationDTO.userId)!!
        val versionObject = this.versionRepository.findByNumber(configurationDTO.version)!!
        configuration.version = versionObject
        this.configurationRepository.save(configuration)
    }

    fun updateRule(ruleDTO: UpdateRuleDTO) {
        val configuration = this.configurationRepository.findByUserId(ruleDTO.userId)!!
        val ruleDescription = this.ruleDescriptionRepository.findByDescription(ruleDTO.description)!!
        val rule = this.ruleRepository.findByRuleDescriptionAndConfiguration(ruleDescription, configuration)!!
        rule.isActive = ruleDTO.isActive
        rule.amount = ruleDTO.amount
        this.ruleRepository.save(rule)
    }

    private fun seedRules(configuration: Configuration){
        val formatting = ruleTypeRepository.findByType("FORMATTING")!!
        val linting = ruleTypeRepository.findByType("LINTING")!!

        val ruleDescriptions = listOf(
            Pair(formatting, "SPACE BETWEEN ASSIGNATION"),
            Pair(formatting, "SPACE BEFORE DECLARATION"),
            Pair(formatting, "SPACE AFTER DECLARATION"),
            Pair(formatting, "LINES AFTER PRINT"),
            Pair(formatting, "TABS AFTER IF"),
            Pair(linting, "IDENTIFIER IN FUNCTION"),
            Pair(linting, "EXPRESSION IN FUNCTION"),
            Pair(linting, "LITERAL IN FUNCTION"),
            Pair(linting, "CAMEL CASE"),
            Pair(linting, "SNAKE CASE")
        )

        ruleDescriptions.forEach { (ruleType, description) ->
            val ruleDescription = ruleDescriptionRepository.findByDescription(description)!!
            this.ruleRepository.save(Rule(ruleType, configuration, ruleDescription, 0, false))
        }
    }
}
