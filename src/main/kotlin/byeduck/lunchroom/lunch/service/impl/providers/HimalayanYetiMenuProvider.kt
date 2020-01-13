package byeduck.lunchroom.lunch.service.impl.providers

import byeduck.lunchroom.domain.MenuItem
import byeduck.lunchroom.lunch.service.MenuProvider
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate

@Service
class HimalayanYetiMenuProvider : MenuProvider, StandardLunchMenuProviderBase() {

    override val meatLunchPrice = 19.00
    override val vegeLunchPrice = 19.00
    private val lambLunchPrice = 26.0

    override fun getCurrentMenu(): List<MenuItem> {
        val today = LocalDate.now().dayOfWeek
        return listOfNotNull(
                getMeatLunchForDay(today),
                getVegeLunchForDay(today),
                getLambLunchForDay(today)
        )
    }

    override fun getMeatLunchForDay(day: DayOfWeek): MenuItem = when (day) {
        DayOfWeek.MONDAY -> generateMeatLunchMenuItem("Kurczak balti")
        DayOfWeek.TUESDAY -> generateMeatLunchMenuItem("Kurczak curry")
        DayOfWeek.WEDNESDAY -> generateMeatLunchMenuItem("Himalayan masala z kurczakiem")
        DayOfWeek.THURSDAY -> generateMeatLunchMenuItem("Kurczak butter masal")
        DayOfWeek.FRIDAY -> generateMeatLunchMenuItem("Kurczak mango")
        else -> generateNoLunchMenuItem()
    }

    override fun getVegeLunchForDay(day: DayOfWeek): MenuItem = when (day) {
        DayOfWeek.MONDAY -> generateVegeLunchMenuItem("Chilli paneer")
        DayOfWeek.TUESDAY -> generateVegeLunchMenuItem("Zielone curry z warzywami")
        DayOfWeek.WEDNESDAY -> generateVegeLunchMenuItem("Butter masala paneer")
        DayOfWeek.THURSDAY -> generateVegeLunchMenuItem("Curry z warzywami")
        DayOfWeek.FRIDAY -> generateVegeLunchMenuItem("Palak paneer")
        else -> generateNoLunchMenuItem()
    }

    private fun getLambLunchForDay(day: DayOfWeek): MenuItem = when (day) {
        DayOfWeek.MONDAY -> generateLambLunchMenuItem("Himalayan masala z baraniną")
        DayOfWeek.TUESDAY -> generateLambLunchMenuItem("Vindaloo z baraniną")
        DayOfWeek.WEDNESDAY -> generateLambLunchMenuItem("Żółte curry z baraniną")
        DayOfWeek.THURSDAY -> generateLambLunchMenuItem("Balti lamb")
        DayOfWeek.FRIDAY -> generateLambLunchMenuItem("Czerwone curry z baraniną")
        else -> generateNoLunchMenuItem()
    }

    private fun generateLambLunchMenuItem(description: String) = MenuItem(description, lambLunchPrice)

}