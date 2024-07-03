package modules.common.seed

import modules.configuration.persistence.entity.Language
import modules.testCase.model.entity.VariableType
import modules.testCase.repository.VariableTypeRepository
import jakarta.transaction.Transactional
import modules.configuration.persistence.repository.LanguageRepository
import modules.rule.persistence.entity.RuleDescription
import modules.rule.persistence.entity.RuleParent
import modules.rule.persistence.entity.RuleType
import modules.rule.persistence.repository.RuleDescriptionRepository
import modules.rule.persistence.repository.RuleParentRepository
import modules.rule.persistence.repository.RuleTypeRepository
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
        val (print, identifierFormat) = saveRuleParent()
        val (readInput, emptyParent) = saveSecondRuleParent()
        saveRuleDescriptionFormat(formatting, emptyParent)
        saveRuleDescriptionSca(linting, print, readInput, identifierFormat)

        saveVariableType()
    }

    private fun saveVariableType() {
        val env = VariableType("ENVIRONMENT")
        val input = VariableType("INPUT")
        val output = VariableType("OUTPUT")
        this.variableTypeRepository.save(env)
        this.variableTypeRepository.save(input)
        this.variableTypeRepository.save(output)
    }

    private fun saveRuleDescriptionSca(
        linting: RuleType,
        print: RuleParent,
        readInput: RuleParent,
        identifierFormat: RuleParent
    ) {
        this.ruleDescriptionRepository.save(RuleDescription("expression", linting, print))
        this.ruleDescriptionRepository.save(RuleDescription("identifier", linting, print))
        this.ruleDescriptionRepository.save(RuleDescription("literal", linting, print))
        this.ruleDescriptionRepository.save(RuleDescription("expression", linting, readInput))
        this.ruleDescriptionRepository.save(RuleDescription("identifier", linting, readInput))
        this.ruleDescriptionRepository.save(RuleDescription("literal", linting, readInput))
        this.ruleDescriptionRepository.save(RuleDescription("snake_case", linting, identifierFormat))
    }

    private fun saveRuleDescriptionFormat(formatting: RuleType, emptyParent: RuleParent) {
        this.ruleDescriptionRepository.save(RuleDescription("SPACES BETWEEN ASSIGNATION", formatting, emptyParent))
        this.ruleDescriptionRepository.save(RuleDescription("SPACES BEFORE DECLARATION", formatting, emptyParent))
        this.ruleDescriptionRepository.save(RuleDescription("SPACES AFTER DECLARATION", formatting, emptyParent))
        this.ruleDescriptionRepository.save(RuleDescription("LINES AFTER PRINT", formatting, emptyParent))
        this.ruleDescriptionRepository.save(RuleDescription("TABS AFTER IF", formatting, emptyParent))
    }

    private fun saveSecondRuleParent(): Pair<RuleParent, RuleParent> {
        val readInput = RuleParent("readinput")
        val emptyParent = RuleParent("")
        this.ruleParentRepository.save(readInput)
        this.ruleParentRepository.save(emptyParent)
        return Pair(readInput, emptyParent)
    }

    private fun saveRuleParent(): Pair<RuleParent, RuleParent> {
        val print = RuleParent("println")
        val identifierFormat = RuleParent("identifier_format")
        this.ruleParentRepository.save(print)
        this.ruleParentRepository.save(identifierFormat)
        return Pair(print, identifierFormat)
    }

    private fun saveRuleType(): Pair<RuleType, RuleType> {
        val formatting = RuleType("FORMATTING")
        val linting = RuleType("LINTING")
        this.ruleTypeRepository.save(formatting)
        this.ruleTypeRepository.save(linting)
        return Pair(formatting, linting)
    }
}


