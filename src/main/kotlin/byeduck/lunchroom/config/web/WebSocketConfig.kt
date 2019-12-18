package byeduck.lunchroom.config.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
@Profile("local", "docker", "heroku")
class WebSocketConfig(
        @Value("\${origins.allowed}")
        private val allowedOrigins: Array<String>
) : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/room")
        registry.setApplicationDestinationPrefixes("/app")
        registry.setUserDestinationPrefix("/room/errors")
        super.configureMessageBroker(registry)
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/propose").setAllowedOrigins(*allowedOrigins)
        registry.addEndpoint("/propose").setAllowedOrigins(*allowedOrigins).withSockJS()
        super.registerStompEndpoints(registry)
    }
}