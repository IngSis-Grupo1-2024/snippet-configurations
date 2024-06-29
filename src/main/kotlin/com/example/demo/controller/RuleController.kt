package com.example.demo.controller

import com.example.demo.dto.output.RulesDto
import com.example.demo.dto.rule.GetRulesDTO
import com.example.demo.dto.rule.InputGetRulesDto
import com.example.demo.dto.rule.UpdateRuleDTO
import com.example.demo.dto.rule.UpdateRulesDTO
import com.example.demo.service.RuleService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
class RuleController(private val ruleService: RuleService) {

    @GetMapping("/rules")
    fun getLintingRules( @AuthenticationPrincipal jwt: Jwt,
                        @RequestParam("ruleType") ruleType: String): ResponseEntity<List<GetRulesDTO>> {
        return ResponseEntity.ok(this.ruleService.getRulesByType(InputGetRulesDto(ruleType), jwt.subject))
    }

    @PutMapping("/rules/update")
    fun addRule(@Valid @RequestBody updateRuleDTO: UpdateRuleDTO, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<String> {
        this.ruleService.updateRule(updateRuleDTO, jwt.subject)
        return ResponseEntity.ok("The rule was updated correctly")
    }

    @PostMapping("/rules/update_rules")
    fun updateRules(@AuthenticationPrincipal jwt: Jwt,
                    @Valid @RequestBody updateRulesDTO: UpdateRulesDTO
    ): ResponseEntity<RulesDto> {
        val userId = jwt.subject
        val rulesDto = this.ruleService.updateRules(updateRulesDTO, userId)
        return ResponseEntity.ok(rulesDto)
    }
}
