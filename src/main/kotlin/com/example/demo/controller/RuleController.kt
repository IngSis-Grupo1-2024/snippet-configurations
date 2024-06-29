package com.example.demo.controller

import com.example.demo.dto.rule.GetRulesDTO
import com.example.demo.dto.rule.InputGetRulesDto
import com.example.demo.dto.rule.UpdateRuleDTO
import com.example.demo.service.RuleService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class RuleController(private val ruleService: RuleService) {

    @GetMapping("/rules")
    fun getLintingRules(@RequestParam("userId") userId: String,
                        @RequestParam("ruleType") ruleType: String): ResponseEntity<List<GetRulesDTO>> {
        return ResponseEntity.ok(this.ruleService.getRulesByType(InputGetRulesDto(userId, ruleType)))
    }

    @PutMapping("/rules/update")
    fun addRule(@Valid @RequestBody updateRuleDTO: UpdateRuleDTO): ResponseEntity<String> {
        this.ruleService.updateRule(updateRuleDTO)
        return ResponseEntity.ok("The rule was updated correctly")
    }
}
