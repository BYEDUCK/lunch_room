package byeduck.lunchroom.lunch.service.impl.providers

import byeduck.lunchroom.domain.MenuItem
import java.time.DayOfWeek

abstract class StandardLunchMenuProviderBase {
    abstract val meatLunchPrice: Double
    abstract val vegeLunchPrice: Double
    private val noLunchMsg = "No lunch for today :("

    abstract fun getMeatLunchForDay(day: DayOfWeek): MenuItem
    abstract fun getVegeLunchForDay(day: DayOfWeek): MenuItem
    fun generateMeatLunchMenuItem(description: String) = MenuItem(description, meatLunchPrice)
    fun generateVegeLunchMenuItem(description: String) = MenuItem(description, vegeLunchPrice)
    fun generateNoLunchMenuItem() = MenuItem(noLunchMsg, 0.00)
}