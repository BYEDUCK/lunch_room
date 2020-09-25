package byeduck.lunchroom.user.service

import byeduck.lunchroom.NICK_COOKIE_NAME
import byeduck.lunchroom.TOKEN_COOKIE_NAME
import byeduck.lunchroom.USER_ID_COOKIE_NAME
import byeduck.lunchroom.token.AuthorizationToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class CookieService(
        @Value("\${front.domain}")
        private val frontDomain: String
) {

    fun setAuthorizationCookies(
            response: HttpServletResponse, userNick: String, userId: String, token: AuthorizationToken
    ) {
        val nickCookie = getStringCookieHeaderValue(NICK_COOKIE_NAME, userNick, null)
        val tokenMaxAge = ((token.expiresOn - System.currentTimeMillis()) / 1000.0).toInt()
        val tokenCookie = getStringCookieHeaderValue(TOKEN_COOKIE_NAME, token.data, tokenMaxAge)
        val userIdCookie = getStringCookieHeaderValue(USER_ID_COOKIE_NAME, userId, null);
        val setCookieHeaderName = "Set-Cookie"
        response.addHeader(setCookieHeaderName, nickCookie)
        response.addHeader(setCookieHeaderName, tokenCookie)
        response.addHeader(setCookieHeaderName, userIdCookie)
    }

    private fun getStringCookieHeaderValue(
            name: String, value: String, maxAge: Int?, sameSite: String = "Strict",
            path: String = "/", domain: String = frontDomain
    ) = StringBuilder()
            .append("$name=$value;")
            .append(if (maxAge != null) "Max-Age=$maxAge;" else "")
            .append(if (domain.contains("byeduck.com")) "Secure;" else "")
            .append("Path=$path;")
            .append("Domain=$domain;")
            .append("SameSite=$sameSite;")
            .toString()
}