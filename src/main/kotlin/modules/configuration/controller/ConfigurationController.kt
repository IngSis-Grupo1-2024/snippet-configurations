package modules.configuration.controller
import modules.common.exception.NotFoundException
import jakarta.validation.Valid
import modules.configuration.input.ConfigurationInput
import modules.configuration.service.ConfigurationService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
class ConfigurationController(private val configurationService: ConfigurationService) {
    private val log = LoggerFactory.getLogger(ConfigurationController::class.java)

    @PostMapping("/configuration")
    fun createConfiguration(@AuthenticationPrincipal jwt: Jwt, @Valid @RequestBody configurationInput: ConfigurationInput): ResponseEntity<String> {
        log.info("Creating default configuration in snippet configuration")
        this.configurationService.createConfiguration(configurationInput, jwt.subject)
        return ResponseEntity.ok().build<String>()
    }

    @PostMapping("/configuration/update_version")
    fun updateVersion(@AuthenticationPrincipal jwt: Jwt, @Valid @RequestBody configurationInput: ConfigurationInput): ResponseEntity<String> {
        this.configurationService.updateVersion(configurationInput, jwt.subject)
        return ResponseEntity.ok().build<String>()
    }

    @GetMapping("/configuration/get_version/{language}")
    fun getVersion(@AuthenticationPrincipal jwt: Jwt, @Valid @PathVariable language: String): ResponseEntity<String> {
        try{
            val version = this.configurationService.getVersionInput(jwt.subject, language)
            return ResponseEntity.status(HttpStatus.OK).body(version)
        } catch (e: NotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
    }
}

