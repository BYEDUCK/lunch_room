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
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.nio.charset.StandardCharsets

@Service
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

    //TODO: to be completed
    override fun sign(authorizationCode: String): SignResponse { // Call google apis for user email
        val accessToken = retrieveAccessToken(authorizationCode)
        throw UnauthorizedException()
    }

    private fun retrieveAccessToken(authorizationCode: String): AccessToken {
        val url = buildUrlForRetrieveAccessToken(authorizationCode)
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_FORM_URLENCODED
        httpHeaders.accept = listOf(MediaType.APPLICATION_JSON)
        try {
            val response = restTemplate.exchange<OAuthAccessTokenResponse>(url, HttpMethod.POST, HttpEntity<Any>(httpHeaders))
            val accessToken = AccessToken.fromOAuthAccessTokenResponse(response.body ?: throw UnauthorizedException())
            logger.info("Got access token from google")
            return accessToken
        } catch (e: RestClientException) {
            logger.error("Error from google: {}", e.message)
            throw e
        }
    }

    private fun buildUrlForRetrieveAccessToken(authorizationCode: String): URI = UriComponentsBuilder.fromUriString(googleOAuthUrl)
            .queryParam("grant_type", "authorization_code")
            .queryParam("code", authorizationCode)
            .queryParam("client_id", clientId)
            .queryParam("client_secret", clientSecret)
            .queryParam("redirect_uri", "http://localhost:4200/signIn")
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri()
}