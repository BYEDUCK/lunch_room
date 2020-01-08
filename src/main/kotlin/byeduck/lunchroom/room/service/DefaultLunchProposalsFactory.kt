package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.LunchProposal
import byeduck.lunchroom.domain.MenuItem

class DefaultLunchProposalsFactory {

    companion object {

        private const val vegeLunchDesc = "Lunch vege"
        private const val meatLunchDesc = "Lunch mięsny"

        fun getDefaults(roomId: String): List<LunchProposal> {
            return listOfNotNull(
                    LunchProposal(
                            roomId,
                            "Ogród smaków",
                            "https://www.facebook.com/Ogrodsmakupolskaismacznakuchnia",
                            listOfNotNull(
                                    MenuItem(meatLunchDesc, 22.40),
                                    MenuItem(vegeLunchDesc, 22.40)
                            )),
                    LunchProposal(
                            roomId,
                            "Katmandu",
                            "http://katmandu.com.pl/?page_id=124",
                            listOfNotNull(
                                    MenuItem(meatLunchDesc, 21.00),
                                    MenuItem(vegeLunchDesc, 21.00),
                                    MenuItem("Lunch jagnięcina", 27.00)
                            )),
                    LunchProposal(
                            roomId,
                            "Rasoi",
                            "https://www.facebook.com/RestauracjaIndyjskaRasoi",
                            listOfNotNull(
                                    MenuItem(meatLunchDesc, 20.00),
                                    MenuItem(vegeLunchDesc, 20.00)
                            )),
                    LunchProposal(
                            roomId,
                            "Himalayan yeti",
                            "https://www.himalayanyeti.com.pl/restauracja/restauracja-himalayan-yeti#menu-lunch-menu",
                            listOfNotNull(
                                    MenuItem(meatLunchDesc, 19.00),
                                    MenuItem(vegeLunchDesc, 19.00)
                            )),
                    LunchProposal(
                            roomId,
                            "Shahi curry (vege+meat 21zł min 5 osób)",
                            "https://www.facebook.com/ShahiCurryRestauracja/",
                            listOfNotNull(
                                    MenuItem(meatLunchDesc, 22.00),
                                    MenuItem(vegeLunchDesc, 22.00)
                            )),
                    LunchProposal(
                            roomId,
                            "Warsaw burger",
                            "http://warsawburgerbar.pl/#wolowina",
                            listOfNotNull(
                                    MenuItem("Klasyk", 21.00)
                            ))
            )
        }
    }
}