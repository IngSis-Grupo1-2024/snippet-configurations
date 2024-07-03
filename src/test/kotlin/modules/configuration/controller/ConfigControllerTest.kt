package modules.configuration.controller

import com.google.gson.Gson
import modules.common.TestSecurity
import modules.common.TestSecurity.Companion.AUTH0ID
import modules.common.seed.Seed
import modules.configuration.input.ConfigurationInput
import modules.configuration.persistence.repository.ConfigurationRepository
import modules.configuration.persistence.repository.LanguageRepository
import modules.rule.persistence.repository.RuleDescriptionRepository
import modules.rule.persistence.repository.RuleParentRepository
import modules.rule.persistence.repository.RuleRepository
import modules.rule.persistence.repository.RuleTypeRepository
import modules.testCase.repository.VariableTypeRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = [TestSecurity::class])
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConfigControllerTest
@Autowired constructor(
    private val mvc: MockMvc,
    private val ruleDescriptionRepository: RuleDescriptionRepository,
    private val languageRepository: LanguageRepository,
    private val ruleTypeRepository: RuleTypeRepository,
    private val variableTypeRepository: VariableTypeRepository,
    private val ruleParentRepository: RuleParentRepository,
    private val ruleRepository: RuleRepository,
    private val configurationRepository: ConfigurationRepository,
    ){
    val base = "/configuration"
    val language = "JAVA"

    @BeforeAll
    fun createRules(){
        val seed = Seed(ruleDescriptionRepository, languageRepository, ruleTypeRepository, variableTypeRepository, ruleParentRepository)
        seed.seedDb()
    }

    @BeforeEach
    fun clearRuleAndConfigRepo(){
        ruleRepository.deleteAll()
        configurationRepository.deleteAll()
    }

    @Test
    fun `001- create default configuration`() {
        val version = "v1"
        val request = getConfigurationInput(version, language)
        mvc.perform(
            post(base)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isOk)

        val descriptors = ruleDescriptionRepository.findAll()
        val rules = ruleRepository.findAll()
        assertEquals(descriptors.size, rules.size)
    }


    @Test
    fun `002- get version`() {
        val version = "v1"
        val request = getConfigurationInput(version, language)
        mvc.perform(
            post(base)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )


        mvc.perform(
            get("$base/get_version/$language")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isOk)

        val lang = languageRepository.findByName(language)
        val config = configurationRepository.findByUserIdAndLanguage(AUTH0ID, lang!!)
        assertEquals(config!!.version, "v1")
    }

    @Test
    fun `003- get version error`() {
        val version = "v1"
        val request = getConfigurationInput(version, language)
        mvc.perform(
            post(base)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )


        mvc.perform(
            get("$base/get_version/PYTHON")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `004- update version`() {
        val v1 = "v1"
        val r1 = getConfigurationInput(v1, language)
        mvc.perform(
            post(base)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(r1)
        )

        val version = "v2"
        val request = getConfigurationInput(version, language)
        mvc.perform(
            post("$base/update_version")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isOk)

        val lang = languageRepository.findByName(language)
        val config = configurationRepository.findByUserIdAndLanguage(AUTH0ID, lang!!)
        assertEquals(config!!.version, "v2")
    }

    @Test
    fun `005- update version of an unsaved language`() {
        val v1 = "v1"
        val r1 = getConfigurationInput(v1, language)
        mvc.perform(
            post(base)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(r1)
        )

        val version = "v2"
        val newLanguage = "PYTHON"
        val request = getConfigurationInput(version, newLanguage)
        mvc.perform(
            post("$base/update_version")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isOk)

        val lang = languageRepository.findByName(newLanguage)
        val config = configurationRepository.findByUserIdAndLanguage(AUTH0ID, lang!!)
        assertEquals(config!!.version, "v2")
    }

    private fun getConfigurationInput(version: String, language: String): String {
        val gson = Gson()
        return gson.toJson(ConfigurationInput(version, language)).toString()
    }
}
