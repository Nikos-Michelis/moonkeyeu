package com.moonkeyeu.core.api.configuration.security.jwt;

import com.moonkeyeu.core.api.security.model.token.jwt.TokenScope;
import com.moonkeyeu.core.api.security.services.jwt.JwtRequestExtractor;
import com.moonkeyeu.core.api.security.services.jwt.JwtServiceParserImpl;
import com.moonkeyeu.core.api.security.services.jwt.JwtServiceProvider;
import com.moonkeyeu.core.api.user.services.impl.UserDetailsServiceImpl;
import com.moonkeyeu.core.api.security.dto.TokenDTO;
import com.moonkeyeu.core.api.user.model.User;
import com.moonkeyeu.core.api.security.repository.TokenRepository;
import com.moonkeyeu.core.api.settings.exceptions.InvalidJwtTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtRequestExtractor jwtRequestExtractor;
    private final JwtServiceParserImpl jwtServiceParserImpl;
    private final JwtServiceProvider jwtServiceProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().matches("^/api/v1/(auth|oauth2|public|images|csrf|community)/.*")) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> extractedJwt = jwtRequestExtractor.extractToken(request);
        //logger.info("ExtractedJwt: " + extractedJwt);
       // logger.info("url: " + request.getRequestURI());
        if (extractedJwt.isPresent()) {
            String jwtToken = extractedJwt.get();
           // logger.info("JWT: " + jwtToken);
            TokenDTO tokenDTO = jwtServiceParserImpl.parseToken(jwtToken);
            //logger.info("AuthContext:" + SecurityContextHolder.getContext().getAuthentication());
            // Extract the username from the token and verify whether the authentication context is already set.
            if (tokenDTO.getUserName() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(tokenDTO.getUserName());
                boolean hasValidRefreshToken =
                        tokenRepository.findAllValidTokenByUser(userDetails.getUserId())
                                .stream()
                                .filter(t1 -> !t1.isRevoked() && !t1.isExpired())
                                .anyMatch(t1 -> jwtServiceProvider.isValidToken(t1.getToken(), userDetails));
                if (jwtServiceProvider.isAccessToken(jwtToken, TokenScope.valueOf(tokenDTO.getTokenScope().name().toUpperCase())) && hasValidRefreshToken) {
                    setSecurityContext(request, userDetails);
                } else {
                   throw new InvalidJwtTokenException("Invalid token scope, or expire token.");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
    private void setSecurityContext(HttpServletRequest request, User userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
       //log.info("Roles from JWT: " + userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
