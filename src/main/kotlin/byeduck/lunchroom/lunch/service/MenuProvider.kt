package byeduck.lunchroom.lunch.service

import byeduck.lunchroom.domain.MenuItem

interface MenuProvider {
    fun getCurrentMenu(): List<MenuItem>
}