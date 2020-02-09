package byeduck.lunchroom.logging

import byeduck.lunchroom.COOKIE_HEADER_NAME
import byeduck.lunchroom.MASK_STRING
import byeduck.lunchroom.TOKEN_COOKIE_NAME
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoggingInterceptor : HandlerInterceptor {

    private val logger: Logger = LoggerFactory.getLogger(LoggingInterceptor::class.java)
    private val startTimeAttributeName = "startTime"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logRequest(request)
        request.setAttribute(startTimeAttributeName, System.currentTimeMillis())
        return super.preHandle(request, response, handler)
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        logResponse(response, request.getAttribute(startTimeAttributeName) as Long)
        super.postHandle(request, response, handler, modelAndView)
    }

    private fun logRequest(request: HttpServletRequest) = logger.debug(
            "\n########REQUEST########\n{}: {}\nPARAMS:\n{}\nHEADERS:\n{}\nCOOKIES:\n{}\n",
            request.method,
            request.requestURI,
            parseParamMapToString(request.parameterMap ?: emptyMap()),
            parseHeadersFromRequestToString(request),
            parseCookiesToString(request.cookies ?: emptyArray())
    )

    private fun logResponse(response: HttpServletResponse, startTime: Long) = logger.debug(
            "\n########RESPONSE########\nHEADERS:\n{}\nSTATUS : {}\nELAPSED TIME: {} ms\n",
            parseHeadersFromResponseToString(response),
            response.status,
            System.currentTimeMillis() - startTime
    )

    private fun parseParamMapToString(paramMap: Map<String, Array<String>>) = StringBuilder()
            .append("{\n")
            .append(paramMap.map {
                "\t\"${it.key}\" : \"${it.value[0]}\""
            }.joinToString("\n"))
            .append("\n}")
            .toString()

    private fun parseCookiesToString(cookies: Array<Cookie>) = StringBuilder()
            .append("{\n")
            .append(cookies.joinToString("\n") {
                "\t\"${it.name}\" : \"${if (it.name == TOKEN_COOKIE_NAME) MASK_STRING else it.value}\""
            })
            .append("\n}")
            .toString()

    private fun parseHeadersFromRequestToString(request: HttpServletRequest) = StringBuilder()
            .append("{\n")
            .append(request.headerNames.toList().joinToString("\n") {
                if (it != COOKIE_HEADER_NAME) "\t\"$it\" : \"${request.getHeader(it)}\"" else ""
            })
            .append("\n}")
            .toString()

    private fun parseHeadersFromResponseToString(response: HttpServletResponse) = StringBuilder()
            .append("{\n")
            .append(response.headerNames.toList().joinToString("\n") {
                "\t\"$it\" : \"${response.getHeader(it)}\""
            })
            .append("\n}")
            .toString()
}