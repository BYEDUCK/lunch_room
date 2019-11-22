package byeduck.lunchroom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
class LunchRoomApplication

fun main(args: Array<String>) {
    runApplication<LunchRoomApplication>(*args)
}
