package byeduck.lunchroom

import byeduck.lunchroom.logging.LoggingInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class LunchRoomConfiguration : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loggingInterceptor())
        super.addInterceptors(registry)
    }

    @Bean
    fun loggingInterceptor(): LoggingInterceptor = LoggingInterceptor()
}