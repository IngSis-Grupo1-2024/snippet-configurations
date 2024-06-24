package com.example.demo.service

import com.example.demo.dto.rule.GetRulesDTO
import com.example.demo.dto.rule.InputGetRulesDto
import com.example.demo.dto.rule.UpdateRuleDTO
import com.example.demo.exception.NotFoundException
import com.example.demo.repository.RuleDescriptionRepository
import com.example.demo.repository.RuleRepository
import com.example.demo.repository.RuleTypeRepository
import org.springframework.stereotype.Service


@Service
class RuleService(
    private val ruleTypeRepository: RuleTypeRepository,
    private val ruleDescriptionRepository: RuleDescriptionRepository,
    private val configurationService: ConfigurationService,
    private val ruleRepository: RuleRepository,
) {

    fun getRulesByType(inputGetRulesDto: InputGetRulesDto): List<GetRulesDTO> {
        try{
            val ruleType = this.ruleTypeRepository.findByType(inputGetRulesDto.ruleType)?: throw NotFoundException("Rule type not found")
            val configuration = this.configurationService.getUserConfiguration(inputGetRulesDto.userId)
            return this.ruleRepository.findByRuleTypeAndConfiguration(ruleType, configuration).map { rule ->
                GetRulesDTO(rule.ruleDescription.description, rule.isActive, rule.amount.toString())
            }
        }catch (e: Exception){
            throw e

        }
    }

    fun updateRule(ruleDTO: UpdateRuleDTO){
        try{
            val ruleDescription = this.ruleDescriptionRepository.findByDescription(ruleDTO.description)?: throw NotFoundException("Rule description was not found")
            val configuration = this.configurationService.getUserConfiguration(ruleDTO.userId)
            val rule = this.ruleRepository.findByRuleDescriptionAndConfiguration(ruleDescription, configuration) ?: throw NotFoundException("Rule was not found")
            rule.isActive = ruleDTO.isActive
            rule.amount = ruleDTO.amount
            this.ruleRepository.save(rule)
        }catch (e:Exception){
            throw e
        }

    }
}
