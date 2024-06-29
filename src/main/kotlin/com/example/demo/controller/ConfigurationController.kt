package com.example.demo.controller
import com.example.demo.dto.configuration.ConfigurationDTO
import com.example.demo.dto.configuration.GetVersionInput
import com.example.demo.dto.output.RulesDto
import com.example.demo.dto.rule.GetRulesDTO
import com.example.demo.dto.rule.InputGetRulesDto
import com.example.demo.dto.rule.UpdateRuleDTO
import com.example.demo.dto.rule.UpdateRulesDTO
import com.example.demo.exception.NotFoundException
import com.example.demo.service.ConfigurationService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigurationController(private val configurationService: ConfigurationService) {

    @PostMapping("/configuration")
    fun createConfiguration(@AuthenticationPrincipal jwt: Jwt, @Valid @RequestBody configurationDTO: ConfigurationDTO): ResponseEntity<String> {
        this.configurationService.createConfiguration(configurationDTO, jwt.subject)
        return ResponseEntity.ok().build<String>()
    }

    @PostMapping("/configuration/update_rule")
    fun updateVersion(@AuthenticationPrincipal jwt: Jwt, @Valid @RequestBody configurationDTO: ConfigurationDTO): ResponseEntity<String> {
        this.configurationService.updateVersion(configurationDTO, jwt.subject)
        return ResponseEntity.ok().build<String>()
    }

    @GetMapping("/configuration/update_rules")
    fun getVersion(@AuthenticationPrincipal jwt: Jwt, @Valid @RequestBody getVersionInput: GetVersionInput): ResponseEntity<String> {
        try{
            val version = this.configurationService.getVersionInput(jwt.subject, getVersionInput)
            return ResponseEntity.status(HttpStatus.OK).body(version)
        } catch (e: NotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
    }
}

