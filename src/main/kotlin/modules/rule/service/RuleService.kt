package modules.rule.service

import modules.rule.dto.RulesDto
import modules.rule.dto.RuleDTO
import modules.rule.input.InputGetRules
import modules.rule.input.UpdateRuleInput
import modules.rule.input.UpdateRulesInput
import modules.common.exception.NotFoundException
import modules.rule.persistence.entity.Rule
import modules.rule.persistence.entity.RuleParent
import modules.rule.persistence.repository.RuleDescriptionRepository
import modules.rule.persistence.repository.RuleParentRepository
import modules.rule.persistence.repository.RuleRepository
import modules.rule.persistence.repository.RuleTypeRepository
import org.springframework.stereotype.Service


@Service
class RuleService(
    private val ruleTypeRepository: RuleTypeRepository,
    private val ruleDescriptionRepository: RuleDescriptionRepository,
    private val ruleRepository: RuleRepository,
    private val ruleParentRepository: RuleParentRepository,
) {

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
            val ruleParent = this.ruleParentRepository.findByName(getLastWord(ruleDTO.name))
            val ruleDescription = this.ruleDescriptionRepository.findByDescriptionAndRuleParent(getFirstWord(ruleDTO.name), ruleParent!!)
                ?: throw NotFoundException("Rule description was not found")
            val rule = this.ruleRepository.findByRuleDescriptionAndUserId(ruleDescription, userId) ?: throw NotFoundException("Rule was not found")
            rule.isActive = ruleDTO.isActive
            rule.amount = ruleDTO.value
            this.ruleRepository.save(rule)
            return rule
        }catch (e:Exception){
            throw e
        }
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

    fun updateRules(updateRulesInput: UpdateRulesInput, userId: String): RulesDto {
        val type: String = updateRulesInput.type
        updateRulesInput.rules.forEach { rule ->
            this.updateRule(rule, userId)
        }
        return getRulesByType(InputGetRules(type), userId)
    }
}
