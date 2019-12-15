package byeduck.lunchroom.config.web

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/room")
        registry.setApplicationDestinationPrefixes("/app")
        super.configureMessageBroker(registry)
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/propose").setAllowedOrigins("*")
        registry.addEndpoint("/propose").setAllowedOrigins("*").withSockJS()
        super.registerStompEndpoints(registry)
    }
}