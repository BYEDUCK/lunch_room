package byeduck.lunchroom.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
        @Id
        var id: String?,
        var nick: String,
        var password: ByteArray,
        var salt: ByteArray,
        var rooms: MutableList<String>,
        var votes: MutableList<Vote>
) {

    constructor(nick: String, password: ByteArray, salt: ByteArray)
            : this(null, nick, password, salt, ArrayList(), ArrayList())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (nick != other.nick) return false
        if (!password.contentEquals(other.password)) return false
        if (!salt.contentEquals(other.salt)) return false
        if (rooms != other.rooms) return false
        if (votes != other.votes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + nick.hashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + salt.contentHashCode()
        result = 31 * result + rooms.hashCode()
        result = 31 * result + votes.hashCode()
        return result
    }

}