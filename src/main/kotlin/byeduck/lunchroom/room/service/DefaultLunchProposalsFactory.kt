package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.LunchProposal
import byeduck.lunchroom.domain.MenuItem
import java.time.DayOfWeek
import java.time.LocalDate

class DefaultLunchProposalsFactory {

    companion object {

        private const val vegeLunchDesc = "Lunch vege"
        private const val meatLunchDesc = "Lunch mięsny"

        fun getDefaults(roomId: String, ownerId: String): List<LunchProposal> = listOfNotNull(
                LunchProposal(
                        roomId,
                        "Ogród smaków",
                        "https://www.facebook.com/Ogrodsmakupolskaismacznakuchnia",
                        listOfNotNull(
                                MenuItem(meatLunchDesc, 22.40),
                                MenuItem(vegeLunchDesc, 22.40)
                        ), ownerId),
                LunchProposal(
                        roomId,
                        "Katmandu",
                        "http://katmandu.com.pl/?page_id=124",
                        listOfNotNull(
                                MenuItem(meatLunchDesc, 21.00),
                                MenuItem(vegeLunchDesc, 21.00),
                                MenuItem("Lunch jagnięcina", 27.00)
                        ), ownerId),
                LunchProposal(
                        roomId,
                        "Rasoi",
                        "https://www.facebook.com/RestauracjaIndyjskaRasoi",
                        listOfNotNull(
                                MenuItem(meatLunchDesc, 20.00),
                                MenuItem(vegeLunchDesc, 20.00)
                        ), ownerId),
                LunchProposal(
                        roomId,
                        "Himalayan yeti",
                        "https://www.himalayanyeti.com.pl/restauracja/restauracja-himalayan-yeti#menu-lunch-menu",
                        listOfNotNull(
                                MenuItem(meatLunchDesc, 19.00),
                                MenuItem(vegeLunchDesc, 19.00)
                        ), ownerId),
                LunchProposal(
                        roomId,
                        "Shahi curry (vege+meat 21zł min 5 osób)",
                        "https://www.facebook.com/ShahiCurryRestauracja/",
                        listOfNotNull(
                                MenuItem(meatLunchDesc, 22.00),
                                MenuItem(vegeLunchDesc, 22.00)
                        ), ownerId),
                LunchProposal(
                        roomId,
                        "Warsaw burger",
                        "http://warsawburgerbar.pl/#wolowina",
                        listOfNotNull(
                                MenuItem("Klasyk", 21.00)
                        ), ownerId),
                LunchProposal(
                        roomId,
                        "Makalu",
                        "https://www.makalunepal.pl/restauracja/restauracja-makalu-nepal",
                        listOfNotNull(
                                MenuItem(meatLunchDesc, 20.00),
                                MenuItem(vegeLunchDesc, 20.00),
                                MenuItem(getMakaluExtra(), 0.00)
                        ), ownerId)
        )

        private fun getMakaluExtra(): String = when (LocalDate.now().dayOfWeek) {
            DayOfWeek.MONDAY -> "extra: MANGO LASSI"
            DayOfWeek.TUESDAY -> "extra: BUTTER NAAN"
            DayOfWeek.WEDNESDAY -> "extra: SAMOSA"
            DayOfWeek.THURSDAY -> "extra: MANGO KUFLI"
            DayOfWeek.FRIDAY -> "extra: GULAB JAMUN"
            else -> "No extra today :("
        }

    }
}