package com.example.demo.service

import com.example.demo.dto.configuration.ConfigurationDTO
import com.example.demo.dto.configuration.GetVersionInput
import com.example.demo.exception.NotFoundException
import com.example.demo.model.*
import com.example.demo.repository.*
import org.springframework.stereotype.Service

@Service
class ConfigurationService(
    private val configurationRepository: ConfigurationRepository,
    private val ruleRepository: RuleRepository,
    private val languageRepository: LanguageRepository,
    private val ruleTypeRepository: RuleTypeRepository,
    private val ruleDescriptionRepository: RuleDescriptionRepository
) {

    fun createConfiguration(configurationDTO: ConfigurationDTO, userId: String) {
        seedLanguage(userId, configurationDTO)
        seedRules(userId)
    }

    fun updateVersion(configurationDTO: ConfigurationDTO, userId: String) {
        val language = this.languageRepository.findByName(configurationDTO.language) ?: this.languageRepository.save(Language(configurationDTO.language))
        val configuration = this.configurationRepository.findByUserIdAndLanguage(userId, language)
        configuration!!.version = configurationDTO.version
        this.configurationRepository.save(configuration)
    }


    private fun seedRules(userId: String){
        val formatting = ruleTypeRepository.findByType("FORMATTING")!!
        val linting = ruleTypeRepository.findByType("LINTING")!!

        val ruleDescriptions = getRulesDescriptors(formatting, linting)

        ruleDescriptions.forEach { (ruleType, description) ->
            val ruleDescription = ruleDescriptionRepository.findByDescription(description)!!
            if(ruleType == linting) this.ruleRepository.save(Rule(ruleType, userId, ruleDescription, null, false))
            else this.ruleRepository.save(Rule(ruleType, userId, ruleDescription, 0, false))
        }
    }

    private fun getRulesDescriptors(
        formatting: RuleType,
        linting: RuleType
    ): List<Pair<RuleType, String>> =
        listOf(
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

    private fun seedLanguage(userId: String, configurationDTO: ConfigurationDTO) {
        val language = this.languageRepository.findByName(configurationDTO.language) ?: this.languageRepository.save(Language(configurationDTO.language))
        val config = configurationRepository.findByUserIdAndLanguage(userId, language)
        if(config != null) return
        configurationRepository.save(Configuration(language, userId, configurationDTO.version))
    }

    fun getVersionInput(userId: String, languageStr: String) : String {
        val language = this.languageRepository.findByName(languageStr)?: throw NotFoundException("Language not found")
        val config = this.configurationRepository.findByUserIdAndLanguage(userId, language)
        return config!!.version
    }

}
