package byeduck.lunchroom.user.services.impl

import byeduck.lunchroom.domain.SignedInUser
import byeduck.lunchroom.domain.User
import byeduck.lunchroom.repositories.UsersRepository
import byeduck.lunchroom.user.exceptions.InvalidCredentialsException
import byeduck.lunchroom.user.exceptions.UserAlreadyExistsException
import byeduck.lunchroom.user.exceptions.UserNotFoundException
import byeduck.lunchroom.user.services.TokenService
import byeduck.lunchroom.user.services.UserService
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
        @Autowired
        private val tokenService: TokenService,
        @Value("\${password.hashing.algorithm}")
        private val hashingAlgorithm: String,
        @Value("\${password.hashing.iteration.count}")
        private val hashingIterations: Int,
        @Value("\${password.hashing.key.length}")
        private val hashingKeyLength: Int,
        @Value("\${password.hashing.salt.size}")
        private val saltSize: Int
) : UserService {

    override fun signIn(nick: String, password: String): SignedInUser {
        val user = usersRepository.findByNick(nick)
        return user.map {
            val hashed = hashPasswordWithSalt(password, it.salt)
            if (hashed.contentEquals(it.password)) {
                SignedInUser(it, tokenService.generateToken(it.nick))
            } else {
                throw InvalidCredentialsException()
            }
        }.orElseThrow { UserNotFoundException(nick) }
    }

    override fun signUp(nick: String, password: String) {
        val foundUser = usersRepository.findByNick(nick)
        if (foundUser.isPresent) {
            throw UserAlreadyExistsException()
        }
        val salt = generateSalt()
        val hashed = hashPasswordWithSalt(password, salt)
        usersRepository.save(User(nick, hashed, salt))
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