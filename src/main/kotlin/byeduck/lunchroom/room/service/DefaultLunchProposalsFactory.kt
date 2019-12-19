package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.LunchProposal
import byeduck.lunchroom.domain.MenuItem

class DefaultLunchProposalsFactory {

    companion object {

        private const val vegeLunchDesc = "Lunch vege"
        private const val meatLunchDesc = "Lunch mięsny"

        fun getDefaults(roomId: String): List<LunchProposal> {
            return listOfNotNull(
                    LunchProposal(roomId, "Ogród smaków", listOfNotNull(
                            MenuItem(meatLunchDesc, 22.40),
                            MenuItem(vegeLunchDesc, 22.40)
                    )),
                    LunchProposal(roomId, "Katmandu", listOfNotNull(
                            MenuItem(meatLunchDesc, 21.00),
                            MenuItem(vegeLunchDesc, 21.00),
                            MenuItem("Lunch jagnięcina", 27.00)
                    )),
                    LunchProposal(roomId, "Rasoi", listOfNotNull(
                            MenuItem(meatLunchDesc, 20.00),
                            MenuItem(vegeLunchDesc, 20.00)
                    )),
                    LunchProposal(roomId, "Himalayan yeti", listOfNotNull(
                            MenuItem(meatLunchDesc, 19.00),
                            MenuItem(vegeLunchDesc, 19.00)
                    ))
            )
        }
    }
}