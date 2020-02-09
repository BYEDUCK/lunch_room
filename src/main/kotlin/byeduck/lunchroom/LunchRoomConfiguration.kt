package byeduck.lunchroom

import byeduck.lunchroom.logging.LoggingInterceptor
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class LunchRoomConfiguration : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loggingInterceptor())
        super.addInterceptors(registry)
    }

    @Bean
    fun restTemplate(): RestTemplate = RestTemplateBuilder().build()

    @Bean
    fun loggingInterceptor(): LoggingInterceptor = LoggingInterceptor()

    @Bean
    fun requestLogFilter(): CommonsRequestLoggingFilter {
        val filter = CommonsRequestLoggingFilter()
        filter.setIncludeHeaders(false)
        filter.setIncludePayload(true)
        filter.setIncludeQueryString(false)
        filter.setMaxPayloadLength(1000)
        filter.setBeforeMessagePrefix("Request handling start for ")
        filter.setAfterMessagePrefix("Request payload:\n")
        return filter
    }
}