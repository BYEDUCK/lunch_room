package byeduck.lunchroom.lunch.service.impl

import byeduck.lunchroom.domain.MenuItem
import byeduck.lunchroom.lunch.service.MenuProvider
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate

@Service
class KatmanduMenuProvider : MenuProvider {

    private val meatLunchPrice = 21.0
    private val vegeLunchPrice = 21.0
    private val lambLunchPrice = 27.0
    private val noLunchMsg = "No lunch for today :("

    override fun getCurrentMenu(): List<MenuItem> {
        val today = LocalDate.now().dayOfWeek
        return listOfNotNull(
                getMeatLunchForDay(today),
                getVegeLunchForDay(today),
                getLambLunchForDay(today)
        )
    }

    private fun getMeatLunchForDay(day: DayOfWeek): MenuItem = when (day) {
        DayOfWeek.MONDAY -> generateMeatLunchMenuItem("Kurczak curry")
        DayOfWeek.TUESDAY -> generateMeatLunchMenuItem("Kurczak w sosie maślanopomidorowym")
        DayOfWeek.WEDNESDAY -> generateMeatLunchMenuItem("Kurczak balty")
        DayOfWeek.THURSDAY -> generateMeatLunchMenuItem("Kurczak w sosie mango")
        DayOfWeek.FRIDAY -> generateMeatLunchMenuItem("Kurczak Kadai")
        else -> generateNoLunchMenuItem()
    }

    private fun getVegeLunchForDay(day: DayOfWeek): MenuItem = when (day) {
        DayOfWeek.MONDAY -> generateVegeLunchMenuItem("Soczewica")
        DayOfWeek.TUESDAY -> generateVegeLunchMenuItem("Warzywa balty")
        DayOfWeek.WEDNESDAY -> generateVegeLunchMenuItem("Ciecierzyca")
        DayOfWeek.THURSDAY -> generateVegeLunchMenuItem("Warzywa maślanopomidorowe")
        DayOfWeek.FRIDAY -> generateVegeLunchMenuItem("Ryba curry")
        else -> generateNoLunchMenuItem()
    }

    private fun getLambLunchForDay(day: DayOfWeek): MenuItem = when (day) {
        DayOfWeek.MONDAY -> generateLambLunchMenuItem("Jagnięcina Curry")
        DayOfWeek.TUESDAY -> generateLambLunchMenuItem("Jagnięcina z pieczarkami")
        DayOfWeek.WEDNESDAY -> generateLambLunchMenuItem("Jagnięcina balty")
        DayOfWeek.THURSDAY -> generateLambLunchMenuItem("Jagnięcina ze szpinakiem")
        DayOfWeek.FRIDAY -> generateLambLunchMenuItem("Jagnięcina kadai")
        else -> generateNoLunchMenuItem()
    }

    private fun generateMeatLunchMenuItem(description: String) = MenuItem(description, meatLunchPrice)
    private fun generateVegeLunchMenuItem(description: String) = MenuItem(description, vegeLunchPrice)
    private fun generateLambLunchMenuItem(description: String) = MenuItem(description, lambLunchPrice)
    private fun generateNoLunchMenuItem() = MenuItem(noLunchMsg, 0.00)
}