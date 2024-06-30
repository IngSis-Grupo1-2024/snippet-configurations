package modules.rule.controller

import modules.rule.dto.RulesDto
import modules.rule.dto.RuleDTO
import modules.rule.input.InputGetRules
import modules.rule.input.UpdateRuleInput
import modules.rule.input.UpdateRulesInput
import jakarta.validation.Valid
import modules.rule.service.RuleService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
class RuleController(private val ruleService: RuleService) {

    private val log = LoggerFactory.getLogger(RuleController::class.java)

    @GetMapping("/rules")
    fun getTypeRules(@AuthenticationPrincipal jwt: Jwt,
                     @RequestParam("ruleType") ruleType: String): ResponseEntity<RulesDto> {
        log.info("Getting ${ruleType.lowercase()} rules")
        return ResponseEntity.ok(this.ruleService.getRulesByType(InputGetRules(ruleType), jwt.subject))
    }

    @PutMapping("/rules/update")
    fun addRule(@Valid @RequestBody updateRuleInput: UpdateRuleInput, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<String> {
        this.ruleService.updateRule(updateRuleInput, jwt.subject)
        return ResponseEntity.ok("The rule was updated correctly")
    }

    @PostMapping("/rules/update_rules")
    fun updateRules(@AuthenticationPrincipal jwt: Jwt,
                    @Valid @RequestBody updateRulesInput: UpdateRulesInput
    ): ResponseEntity<RulesDto> {
        val userId = jwt.subject
        val rulesDto = this.ruleService.updateRules(updateRulesInput, userId)
        return ResponseEntity.ok(rulesDto)
    }
}
