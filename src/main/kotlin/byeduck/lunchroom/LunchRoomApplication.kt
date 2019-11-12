package byeduck.lunchroom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LunchRoomApplication

fun main(args: Array<String>) {
	runApplication<LunchRoomApplication>(*args)
}
