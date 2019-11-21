package byeduck.lunchroom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableWebMvc
class LunchRoomApplication

fun main(args: Array<String>) {
	runApplication<LunchRoomApplication>(*args)
}
