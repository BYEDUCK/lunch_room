package byeduck.lunchroom

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class LunchRoomConfiguration : WebMvcConfigurer {
    @Bean
    fun restTemplate(): RestTemplate = RestTemplateBuilder().build()
}