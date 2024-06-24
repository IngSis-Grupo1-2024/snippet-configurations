package com.example.demo.controller
import com.example.demo.dto.configuration.ConfigurationDTO
import com.example.demo.dto.rule.GetRulesDTO
import com.example.demo.dto.rule.InputGetRulesDto
import com.example.demo.dto.rule.UpdateRuleDTO
import com.example.demo.service.ConfigurationService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigurationController(private val configurationService: ConfigurationService) {

    @PostMapping("/configuration")
    fun createConfiguration(@Valid @RequestBody configurationDTO: ConfigurationDTO): ResponseEntity<String> {
        this.configurationService.createConfiguration(configurationDTO)
        return ResponseEntity.ok("The configuration was created correctly")
    }

    @PutMapping("/configuration")
    fun updateVersion(@Valid @RequestBody configurationDTO: ConfigurationDTO): ResponseEntity<String> {
        this.configurationService.updateConfiguration(configurationDTO)
        return ResponseEntity.ok("The configuration was updated correctly")
    }
}

