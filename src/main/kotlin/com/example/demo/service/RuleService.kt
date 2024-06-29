package com.example.demo.service

import com.example.demo.dto.output.RulesDto
import com.example.demo.dto.rule.GetRulesDTO
import com.example.demo.dto.rule.InputGetRulesDto
import com.example.demo.dto.rule.UpdateRuleDTO
import com.example.demo.dto.rule.UpdateRulesDTO
import com.example.demo.exception.NotFoundException
import com.example.demo.model.Rule
import com.example.demo.repository.RuleDescriptionRepository
import com.example.demo.repository.RuleRepository
import com.example.demo.repository.RuleTypeRepository
import org.springframework.stereotype.Service


@Service
class RuleService(
    private val ruleTypeRepository: RuleTypeRepository,
    private val ruleDescriptionRepository: RuleDescriptionRepository,
    private val ruleRepository: RuleRepository,
) {

    fun getRulesByType(inputGetRulesDto: InputGetRulesDto, userId: String): List<GetRulesDTO> {
        try{
            val ruleType = this.ruleTypeRepository.findByType(inputGetRulesDto.ruleType)?: throw NotFoundException("Rule type not found")
            return this.ruleRepository.findByRuleTypeAndUserId(ruleType, userId).map { rule ->
                GetRulesDTO(rule.id!!,rule.ruleDescription.description, rule.isActive, rule.amount, rule.ruleDescription.ruleParent.name)
            }
        }catch (e: Exception){
            throw e

        }
    }

    fun updateRule(ruleDTO: UpdateRuleDTO, userId: String): Rule{
        try{
            val ruleDescription = this.ruleDescriptionRepository.findByDescription(ruleDTO.name)?: throw NotFoundException("Rule description was not found")
            val rule = this.ruleRepository.findByRuleDescriptionAndUserId(ruleDescription, userId) ?: throw NotFoundException("Rule was not found")
            rule.isActive = ruleDTO.isActive
            rule.amount = ruleDTO.value
            this.ruleRepository.save(rule)
            return rule
        }catch (e:Exception){
            throw e
        }
    }

    fun updateRules(updateRulesDTO: UpdateRulesDTO, userId: String): RulesDto {
        val type: String = updateRulesDTO.type
        updateRulesDTO.rules.forEach { rule ->
            this.updateRule(rule, userId)
        }
        return RulesDto(getRulesByType(InputGetRulesDto(type), userId))
    }
}
