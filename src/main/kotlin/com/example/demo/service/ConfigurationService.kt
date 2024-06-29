package com.example.demo.service

import com.example.demo.dto.configuration.ConfigurationDTO
import com.example.demo.exception.NotFoundException
import com.example.demo.model.*
import com.example.demo.repository.*
import org.springframework.stereotype.Service

@Service
class ConfigurationService(
    private val configurationRepository: ConfigurationRepository,
    private val ruleRepository: RuleRepository,
    private val languageRepository: LanguageRepository,
    private val ruleDescriptionRepository: RuleDescriptionRepository,
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
        val ruleDescriptions = ruleDescriptionRepository.findAll()

        ruleDescriptions.forEach { description ->
            if(description.ruleType.type == "LINTING") this.ruleRepository.save(Rule(description.ruleType, userId, description, null, false))
            else this.ruleRepository.save(Rule(description.ruleType, userId, description, 0, false))
        }
    }

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
