package byeduck.lunchroom

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.SimpMessagingTemplate

@Configuration
@Profile("test")
class TestConfiguration {

    @Bean(name = ["msgTemplate"])
    fun msgTemplate(): SimpMessagingTemplate {
        return SimpMessagingTemplate(MessageChannel { _, _ -> false })
    }

}