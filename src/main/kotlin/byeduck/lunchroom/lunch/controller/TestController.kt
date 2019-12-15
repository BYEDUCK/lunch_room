package byeduck.lunchroom.lunch.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component

@Component
class TestController {

    private val logger: Logger = LoggerFactory.getLogger(TestController::class.java)

    @MessageMapping("/propose")
    @SendTo("/room/proposals")
    fun testFun(message: TestMessage): String {
        logger.info("From websockets!!: {}", message.msg)
        return "Hello"
    }

}