package modules.rule.service

import modules.rule.dto.RulesDto
import modules.rule.dto.RuleDTO
import modules.rule.input.InputGetRules
import modules.rule.input.UpdateRuleInput
import modules.rule.input.UpdateRulesInput
import modules.common.exception.NotFoundException
import modules.rule.persistence.entity.Rule
import modules.rule.persistence.entity.RuleDescription
import modules.rule.persistence.repository.RuleDescriptionRepository
import modules.rule.persistence.repository.RuleParentRepository
import modules.rule.persistence.repository.RuleRepository
import modules.rule.persistence.repository.RuleTypeRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class RuleService(
    private val ruleTypeRepository: RuleTypeRepository,
    private val ruleDescriptionRepository: RuleDescriptionRepository,
    private val ruleRepository: RuleRepository,
    private val ruleParentRepository: RuleParentRepository,
) {
    private val log = LoggerFactory.getLogger(RuleService::class.java)

    fun getRulesByType(inputGetRulesDto: InputGetRules, userId: String): RulesDto {
        try{
            val ruleType = this.ruleTypeRepository.findByType(inputGetRulesDto.ruleType)?: throw NotFoundException("Rule type not found")
            return RulesDto(this.ruleRepository.findByRuleTypeAndUserId(ruleType, userId).map { rule ->
                RuleDTO(rule.id!!,rule.ruleDescription.description, rule.isActive, rule.amount, rule.ruleDescription.ruleParent.name)
            })
        }catch (e: Exception){
            throw e
        }
    }

    fun updateRule(ruleDTO: UpdateRuleInput, userId: String): Rule {
        try{
            val ruleDescription =
                this.ruleDescriptionRepository.findByDescription(ruleDTO.name)
                    ?: throw NotFoundException("Rule description was not found")
            return updateSpecificRule(ruleDescription, userId, ruleDTO)
        }catch (e:Exception){
            throw e
        }
    }

    private fun updateSpecificRule(
        ruleDescription: RuleDescription?,
        userId: String,
        ruleDTO: UpdateRuleInput
    ): Rule {
        if(ruleDescription == null) throw NotFoundException("Rule description was not found")
        val rule = this.ruleRepository.findByRuleDescriptionAndUserId(ruleDescription, userId)
            ?: throw NotFoundException("Rule was not found")
        rule.isActive = ruleDTO.isActive
        rule.amount = ruleDTO.value
        log.info("Saving update rule ${ruleDTO.name} to ${ruleDTO.value} and ${ruleDTO.isActive}")
        this.ruleRepository.save(rule)
        return rule
    }


    fun updateRules(updateRulesInput: UpdateRulesInput, userId: String): RulesDto {
        if(isLinting(updateRulesInput.type)) updateLintingRules(updateRulesInput, userId)
        else updateFormatingRules(updateRulesInput, userId)
        log.info("Updated rules for ${updateRulesInput.type}")
        return getRulesByType(InputGetRules(updateRulesInput.type), userId)
    }

    private fun updateFormatingRules(updateRulesInput: UpdateRulesInput, userId: String) =
        updateRulesInput.rules.forEach { rule ->
            this.updateFormatingRule(rule, userId)
        }

    private fun updateFormatingRule(ruleDTO: UpdateRuleInput, userId: String): Rule {
        val ruleDescription = this.ruleDescriptionRepository.findByDescription(ruleDTO.name)
        return updateSpecificRule(ruleDescription, userId, ruleDTO)
    }

    private fun updateLintingRules(updateRulesInput: UpdateRulesInput, userId: String) =
        updateRulesInput.rules.forEach { rule ->
            this.updateLintingRule(rule, userId)
        }

    private fun updateLintingRule(ruleDTO: UpdateRuleInput, userId: String): Rule {
        val ruleParent = this.ruleParentRepository.findByName(getLastWord(ruleDTO.name))
        val ruleDescription =
            this.ruleDescriptionRepository.findByDescriptionAndRuleParent(getFirstWord(ruleDTO.name), ruleParent!!)
        return updateSpecificRule(ruleDescription, userId, ruleDTO)
    }

    private fun getLastWord(name: String): String {
        val trimmedInput = name.trim()
        val words = trimmedInput.split("\\s+".toRegex())
        return words.last()
    }

    private fun getFirstWord(name: String): String {
        val trimmedInput = name.trim()
        val words = trimmedInput.split("\\s+".toRegex())
        return if (words.size >= 2) words.dropLast(2).joinToString(" ")
        else ""
    }

    private fun isLinting(type: String): Boolean = type == "LINTING"
}
