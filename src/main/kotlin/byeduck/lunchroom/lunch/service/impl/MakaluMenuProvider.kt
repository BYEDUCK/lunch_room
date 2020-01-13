package byeduck.lunchroom.lunch.service.impl

import byeduck.lunchroom.domain.MenuItem
import byeduck.lunchroom.lunch.service.MenuProvider
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate

@Service
class MakaluMenuProvider : MenuProvider {

    private val meatLunchPrice = 20.0
    private val vegeLunchPrice = 20.0
    private val freePrice = 0.00
    private val noLunchMsg = "No lunch for today :("

    override fun getCurrentMenu(): List<MenuItem> {
        val today = LocalDate.now().dayOfWeek
        return listOfNotNull(
                getMeatLunchForDay(today),
                getVegeLunchForDay(today),
                getExtraForDay(today)
        )
    }

    private fun getMeatLunchForDay(day: DayOfWeek): MenuItem = when (day) {
        DayOfWeek.MONDAY -> generateMeatLunchMenuItem("Chicken Tikka Masala")
        DayOfWeek.TUESDAY -> generateMeatLunchMenuItem("Chicken Methi")
        DayOfWeek.WEDNESDAY -> generateMeatLunchMenuItem("Chicken Korma")
        DayOfWeek.THURSDAY -> generateMeatLunchMenuItem("Butter Chicken")
        DayOfWeek.FRIDAY -> generateMeatLunchMenuItem("Chicken Curry")
        else -> generateNoLunchMenuItem()
    }

    private fun getVegeLunchForDay(day: DayOfWeek): MenuItem = when (day) {
        DayOfWeek.MONDAY -> generateVegeLunchMenuItem("Dal Mix Fry")
        DayOfWeek.TUESDAY -> generateVegeLunchMenuItem("Vegetable Achari")
        DayOfWeek.WEDNESDAY -> generateVegeLunchMenuItem("Chicken Korma")
        DayOfWeek.THURSDAY -> generateVegeLunchMenuItem("Palak Paneer")
        DayOfWeek.FRIDAY -> generateVegeLunchMenuItem("Dal Makhani")
        else -> generateNoLunchMenuItem()
    }

    private fun getExtraForDay(day: DayOfWeek): MenuItem = when (day) {
        DayOfWeek.MONDAY -> MenuItem("extra: MANGO LASSI", freePrice)
        DayOfWeek.TUESDAY -> MenuItem("extra: BUTTER NAAN", freePrice)
        DayOfWeek.WEDNESDAY -> MenuItem("extra: SAMOSA", freePrice)
        DayOfWeek.THURSDAY -> MenuItem("extra: MANGO KUFLI", freePrice)
        DayOfWeek.FRIDAY -> MenuItem("extra: GULAB JAMUN", freePrice)
        else -> MenuItem("No extra today :(", freePrice)
    }


    private fun generateMeatLunchMenuItem(description: String) = MenuItem(description, meatLunchPrice)
    private fun generateVegeLunchMenuItem(description: String) = MenuItem(description, vegeLunchPrice)
    private fun generateNoLunchMenuItem() = MenuItem(noLunchMsg, freePrice)
}