package com.example.demo.service

import com.example.demo.dto.configuration.ConfigurationDTO
import com.example.demo.dto.output.RulesDto
import com.example.demo.dto.rule.GetRulesDTO
import com.example.demo.dto.rule.InputGetRulesDto
import com.example.demo.dto.rule.UpdateRuleDTO
import com.example.demo.exception.NotFoundException
import com.example.demo.model.*
import com.example.demo.repository.*
import org.springframework.stereotype.Service

@Service
class ConfigurationService(
    private val configurationRepository: ConfigurationRepository,
    private val versionRepository: VersionRepository,
    private val ruleRepository: RuleRepository,
    private val languageRepository: LanguageRepository,
    private val ruleTypeRepository: RuleTypeRepository,
    private val ruleDescriptionRepository: RuleDescriptionRepository
) {

    fun createConfiguration(configurationDTO: ConfigurationDTO) {
        val versionObject = this.versionRepository.findByNumber(configurationDTO.version) ?: this.versionRepository.save(Version(configurationDTO.version))
        val language = this.languageRepository.findByName(configurationDTO.language) ?: this.languageRepository.save(Language(configurationDTO.language))
        var configuration = this.configurationRepository.findByUserIdAndVersionAndLanguage(configurationDTO.userId, versionObject, language)
        if(configuration == null) {
            configuration = this.configurationRepository.save(Configuration(versionObject, language, configurationDTO.userId))
            seedRules(configuration)
        }
    }

    fun getRulesByType(inputGetRulesDto: InputGetRulesDto): RulesDto {
        val ruleType = this.ruleTypeRepository.findByType(inputGetRulesDto.ruleType)!!
        val configuration = this.configurationRepository.findByUserId(inputGetRulesDto.userId).first()
            ?: throw NotFoundException("The user doesn't have rules.")
        val rules: List<Rule> = this.ruleRepository.findByRuleTypeAndConfiguration(ruleType, configuration)
        return RulesDto(rules.map { rule ->
           GetRulesDTO(rule.id!!, rule.ruleDescription.description, rule.isActive, rule.amount)
        })
    }

    fun updateVersion(configurationDTO: ConfigurationDTO) {
        val configuration = this.configurationRepository.findByUserId(configurationDTO.userId).first()
        val versionObject = this.versionRepository.findByNumber(configurationDTO.version)!!
        configuration.version = versionObject
        this.configurationRepository.save(configuration)
    }

    fun updateRule(ruleDTO: UpdateRuleDTO, userId: String): String {
        val configuration = this.configurationRepository.findByUserId(userId).first()
        val ruleDescription = this.ruleDescriptionRepository.findByDescription(ruleDTO.name)!!
        val rule = this.ruleRepository.findByRuleDescriptionAndConfiguration(ruleDescription, configuration)!!
        rule.isActive = ruleDTO.isActive
        if (ruleDTO.value != null) {
            if(ruleDTO.value!! > -1) rule.amount = ruleDTO.value!!
        }
        this.ruleRepository.save(rule)
        return rule.ruleType.type
    }

    fun updateRules(updateRulesDTO: List<UpdateRuleDTO>, userId: String): RulesDto {
        val type: String = updateRule(updateRulesDTO.first(), userId)
        updateRulesDTO.forEach { rule ->
            this.updateRule(rule, userId)
        }
        return getRulesByType(InputGetRulesDto(userId, type))
    }

    private fun seedRules(configuration: Configuration){
        val formatting = ruleTypeRepository.findByType("FORMATTING")!!
        val linting = ruleTypeRepository.findByType("LINTING")!!

        val ruleDescriptions = listOf(
            Pair(formatting, "SPACES BETWEEN ASSIGNATION"),
            Pair(formatting, "SPACES BEFORE DECLARATION"),
            Pair(formatting, "SPACES AFTER DECLARATION"),
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
            if(ruleType == linting) this.ruleRepository.save(Rule(ruleType, configuration, ruleDescription, null, false))
            else this.ruleRepository.save(Rule(ruleType, configuration, ruleDescription, 0, false))
        }
    }
}
