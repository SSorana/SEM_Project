package nl.tudelft.sem.template.authentication.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import nl.tudelft.sem.template.authentication.entities.NetworkResponse;
import nl.tudelft.sem.template.authentication.security.CustomUserDetailsService;
import nl.tudelft.sem.template.authentication.security.NetworkUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private final transient CustomUserDetailsService userDetailsService;

    private transient String secretKey = "mysecret";

    public JwtTokenProvider(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Initializes the secret key.
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * creates a json web token.
     *
     * @param username username.
     * @param request http request.
     * @return json web token.
     */
    public String createToken(String username, HttpServletRequest request) {
        NetworkResponse network = NetworkUtils.getDeviceAddresses.apply(request);
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .claim("mac", network.getMacAddress())
                .claim("ip", network.getIpAddress())
                .setIssuer(secretKey)
                .setExpiration(new Date(now.getTime() + 3600000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    /**
     * Creates a UsernamePasswordAuthenticationToken based on the jwt.
     *
     * @param token jwt.
     * @return UsernamePasswordAuthenticationToken.
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Retrieves the username from the encrypted token.
     *
     * @param token jwt.
     * @return username.
     */
    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Filters the token from the request.
     *
     * @param req request.
     * @return token as a string.
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");

        return (bearerToken != null && bearerToken.startsWith("Bearer"))
                ? bearerToken.substring(7, bearerToken.length()) : null;
    }

    /**
     * Validates that the token is in date.
     *
     * @param token jwt.
     * @return if token is in date.
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return (!claims.getBody().getExpiration().before(new Date()));
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
