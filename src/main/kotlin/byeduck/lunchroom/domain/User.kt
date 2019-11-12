package byeduck.lunchroom.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
        @Id
        var id: String?,
        var nick: String,
        var password: String,
        var salt: String
)