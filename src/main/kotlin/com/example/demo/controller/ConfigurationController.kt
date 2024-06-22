package com.example.demo.controller
import com.example.demo.dto.configuration.ConfigurationDTO
import com.example.demo.dto.output.RulesDto
import com.example.demo.dto.rule.GetRulesDTO
import com.example.demo.dto.rule.InputGetRulesDto
import com.example.demo.dto.rule.UpdateRuleDTO
import com.example.demo.exception.NotFoundException
import com.example.demo.service.ConfigurationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigurationController(private val configurationService: ConfigurationService) {

    @PostMapping("/configuration")
    fun createConfiguration(@Valid @RequestBody configurationDTO: ConfigurationDTO): ResponseEntity<String> {
        this.configurationService.createConfiguration(configurationDTO)
        return ResponseEntity.ok().build<String>()
    }

    @PostMapping("/configuration/update_rule")
    fun addRule(@Valid @RequestBody updateRuleDTO: UpdateRuleDTO): ResponseEntity<String> {
        this.configurationService.updateRule(updateRuleDTO)
        return ResponseEntity.ok().build<String>()
    }

    @PostMapping("/configuration/update")
    fun updateVersion(@Valid @RequestBody configurationDTO: ConfigurationDTO): ResponseEntity<String> {
        this.configurationService.updateVersion(configurationDTO)
        return ResponseEntity.ok().build<String>()
    }

    @GetMapping("/configuration/rules")
    fun getLintingRules(@RequestParam("userId") userId: String,
                        @RequestParam("ruleType") ruleType: String): ResponseEntity<Any> {
        try {
            val rules = this.configurationService.getRulesByType(InputGetRulesDto(userId, ruleType))
            return ResponseEntity.ok(rules)
        } catch (e: NotFoundException) {
            val response = ResponseEntity.status(HttpStatus.NOT_FOUND)
            return response.body("The user has no $ruleType rules.")
        }
    }
}

