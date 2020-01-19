package byeduck.lunchroom.user.oauth.google

import byeduck.lunchroom.error.exceptions.UnauthorizedException
import byeduck.lunchroom.user.controller.SignResponse
import byeduck.lunchroom.user.oauth.AccessToken
import byeduck.lunchroom.user.oauth.OAuthAccessTokenResponse
import byeduck.lunchroom.user.oauth.OAuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.nio.charset.StandardCharsets

class GoogleOAuthService(
        @Autowired
        private val restTemplate: RestTemplate,
        @Value("\${oauth.google.url}")
        private val googleOAuthUrl: String,
        @Value("\${oauth.client.id}")
        private val clientId: String,
        @Value("\${oauth.client.secret}")
        private val clientSecret: String
) : OAuthService {

    private val logger: Logger = LoggerFactory.getLogger(GoogleOAuthService::class.java)

    override fun sign(authorizationCode: String): SignResponse {
        TODO("not implemented") // Call google apis for user email
    }

    private fun retrieveAccessToken(authorizationCode: String): AccessToken {
        val url = buildUrlForRetrieveAccessToken(authorizationCode)
        val response = restTemplate.postForEntity(url, null, OAuthAccessTokenResponse::class.java)
        val accessToken = AccessToken.fromOAuthAccessTokenResponse(response.body ?: throw UnauthorizedException())
        logger.info("Got access token from google")
        return accessToken
    }

    private fun buildUrlForRetrieveAccessToken(authorizationCode: String): URI = UriComponentsBuilder.fromUriString(googleOAuthUrl)
            .queryParam("code", authorizationCode)
            .queryParam("client_id", clientId)
            .queryParam("client_secret", clientSecret)
            .queryParam("redirect_uri", "")
            .queryParam("grant_type", "authorization_code")
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri()
}