package nl.tudelft.sem.template.authentication.security.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Custom entry point.
 */
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    /**
     * Commences a custom authentication scheme.
     *
     * @param request       that resulted in an AuthenticationException.
     * @param response      so that the user agent can begin authentication.
     * @param authException that cause the invocation.
     * @throws IOException      an io exception.
     * @throws ServletException a servlet exception.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Jwt authentication failure");
    }
}
