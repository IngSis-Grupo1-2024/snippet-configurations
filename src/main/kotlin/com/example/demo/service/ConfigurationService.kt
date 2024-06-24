package com.example.demo.service

import com.example.demo.dto.configuration.ConfigurationDTO
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
        try {
            val versionObject = this.versionRepository.findByNumber(configurationDTO.version)?: throw NotFoundException("Version not found")
            val language = this.languageRepository.findByName(configurationDTO.language) ?: throw NotFoundException("Language not found")
            val configuration = this.configurationRepository.save(Configuration(versionObject, language,configurationDTO.userId))
            seedRules(configuration)
        }catch (e: Exception){
            throw e
        }
    }

    fun getUserConfiguration(userId:String):Configuration{
        try {
            val configuration = this.configurationRepository.findByUserId(userId)?: throw NotFoundException("The user has no configuration")
            return configuration
        }catch (e:Exception){
            throw e
        }
    }

    fun updateConfiguration(configurationDTO: ConfigurationDTO) {
        try {
            val configuration = this.getUserConfiguration(configurationDTO.userId)
            val language = this.languageRepository.findByName(configurationDTO.language)?: throw NotFoundException("The language was not found")
            val version = this.versionRepository.findByNumber(configurationDTO.version)?: throw NotFoundException("The version was not found")
            configuration.version = version
            configuration.language = language
            this.configurationRepository.save(configuration)

        }catch (e: Exception){
            throw e
        }
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
