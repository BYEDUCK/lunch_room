package byeduck.lunchroom.user

import byeduck.lunchroom.domain.User
import byeduck.lunchroom.repositories.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

@Service
class UserServiceImpl(
        @Autowired
        private val usersRepository: UsersRepository,
        @Value("\${password.hashing.algorithm}")
        private val hashingAlgorithm: String,
        @Value("\${password.hashing.iteration.count}")
        private val hashingIterations: Int,
        @Value("\${password.hashing.key.length}")
        private val hashingKeyLength: Int,
        @Value("\${password.hashing.salt.size}")
        private val saltSize: Int
) : UserService {

    override fun checkPassword(nick: String, password: String): Boolean {
        val user = usersRepository.findByNick(nick)
        var authenticated = false
        user.ifPresentOrElse({
            val hashed = hashPasswordWithSalt(password, it.salt)
            authenticated = hashed.contentEquals(it.password)
        }, {
            throw UserNotFoundException("User with nick \"$nick\" not found.")
        })
        return authenticated
    }

    override fun saveUser(nick: String, password: String): User {
        val salt = generateSalt()
        val hashed = hashPasswordWithSalt(password, salt)
        return usersRepository.save(User(null, nick, hashed, salt))
    }

    private fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(saltSize)
        random.nextBytes(salt)
        return salt
    }

    private fun hashPasswordWithSalt(password: String, salt: ByteArray): ByteArray {
        val keySpec: KeySpec = PBEKeySpec(password.toCharArray(), salt, hashingIterations, hashingKeyLength)
        val secretKeyFactory: SecretKeyFactory = SecretKeyFactory.getInstance(hashingAlgorithm)
        return secretKeyFactory.generateSecret(keySpec).encoded
    }
}