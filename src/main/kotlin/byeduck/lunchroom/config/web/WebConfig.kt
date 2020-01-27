package byeduck.lunchroom.config.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@Profile("local", "docker", "prod")
class WebConfig(
        @Value("\${origins.allowed}")
        private val allowedOrigins: Array<String>,
        @Value("\${front.allowed.methods}")
        private val allowedMethods: Array<String>
) : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
                .addMapping("/**")
                .allowedOrigins(*allowedOrigins)
                .allowedMethods(*allowedMethods)
        super.addCorsMappings(registry)
    }
}