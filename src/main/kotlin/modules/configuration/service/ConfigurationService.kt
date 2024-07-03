package modules.configuration.service

import modules.common.exception.NotFoundException
import modules.configuration.input.ConfigurationInput
import modules.configuration.persistence.entity.Configuration
import modules.configuration.persistence.entity.Language
import modules.configuration.persistence.repository.ConfigurationRepository
import modules.configuration.persistence.repository.LanguageRepository
import modules.rule.persistence.entity.Rule
import modules.rule.persistence.repository.RuleDescriptionRepository
import modules.rule.persistence.repository.RuleRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ConfigurationService(
    private val configurationRepository: ConfigurationRepository,
    private val ruleRepository: RuleRepository,
    private val languageRepository: LanguageRepository,
    private val ruleDescriptionRepository: RuleDescriptionRepository,
) {
    private val log = LoggerFactory.getLogger(ConfigurationService::class.java)

    fun createConfiguration(configurationInput: ConfigurationInput, userId: String) {
        seedLanguage(userId, configurationInput)
        seedRules(userId)
    }

    fun updateVersion(configurationInput: ConfigurationInput, userId: String) {
        val language = this.languageRepository.findByName(configurationInput.language)
        if(language == null) {
            seedLanguage(userId, configurationInput)
            return
        }
        val configuration = this.configurationRepository.findByUserIdAndLanguage(userId, language)
        configuration!!.version = configurationInput.version
        this.configurationRepository.save(configuration)
    }


    private fun seedRules(userId: String){
        val rules = this.ruleRepository.findByUserId(userId)
        if(rules.isNotEmpty()) {
            log.info("Configuration for rules already exists")
            return
        }
        log.info("Creating configuration of rules for $userId")
        val ruleDescriptions = ruleDescriptionRepository.findAll()

        ruleDescriptions.forEach { description ->
            if(description.ruleType.type == "LINTING") this.ruleRepository.save(Rule(description.ruleType, userId, description, null, false))
            else this.ruleRepository.save(Rule(description.ruleType, userId, description, 0, false))
        }
        log.info("Configuration for rules was saved")
    }

    private fun seedLanguage(userId: String, configurationInput: ConfigurationInput) {
        log.info("Creating configuration for language ${configurationInput.language}")
        val language = this.languageRepository.findByName(configurationInput.language) ?: this.languageRepository.save(
            Language(configurationInput.language)
        )
        val config = configurationRepository.findByUserIdAndLanguage(userId, language)
        if(config != null) {
            log.info("Configuration for language ${configurationInput.language} already exists")
            return
        }
        configurationRepository.save(Configuration(language, userId, configurationInput.version))
        log.info("Configuration for language ${configurationInput.language} was saved")
    }

    fun getVersionInput(userId: String, languageStr: String) : String {
        val language = this.languageRepository.findByName(languageStr) ?:
            throw NotFoundException("There is no language for $languageStr")
        val config = this.configurationRepository.findByUserIdAndLanguage(userId, language)
        return config!!.version
    }

}
