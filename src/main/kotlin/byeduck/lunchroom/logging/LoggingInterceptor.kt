package byeduck.lunchroom.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoggingInterceptor : HandlerInterceptorAdapter() {

    private val logger: Logger = LoggerFactory.getLogger(LoggingInterceptor::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.debug(parseRequest(request))
        return super.preHandle(request, response, handler)
    }

    private fun parseRequest(request: HttpServletRequest): String {
        val builder = StringBuilder("Request: {")
        builder.append("\n\tmethod : ${request.method}")
                .append("\n\tcontextPath : ${request.contextPath}")
                .append("\n\tqueryString : ${request.queryString}")
                .append("\n\trequestUrl : ${request.requestURI}")
                .append("\n}")
        return builder.toString()
    }
}