package byeduck.lunchroom.user.service.impl

import byeduck.lunchroom.repositories.UsersRepository
import byeduck.lunchroom.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        @Autowired
        private val usersRepository: UsersRepository
) : UserService {
    override fun isNickAvailable(nick: String): Boolean = nick.isNotBlank() && !usersRepository.findByNick(nick).isPresent
}