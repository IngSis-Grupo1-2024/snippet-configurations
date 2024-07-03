package modules.common

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import java.time.Instant

@TestConfiguration
class TestSecurity {
    @Bean
    @Primary
    fun mockJwtDecoder(): JwtDecoder {
        return JwtDecoder{ jwt() }
    }

    private fun jwt(): Jwt {
        val xxx =
            mapOf<String, Any>(
                "sub" to AUTH0ID
            )
        return Jwt(
            AUTH_TOKEN,
            Instant.now(),
            Instant.now().plusSeconds(30),
            mapOf<String, Any>("str" to "none"),
            xxx
        )
    }

    companion object{
        const val AUTH0ID = "auth|123"
        const val AUTH_TOKEN = "123"
    }
}