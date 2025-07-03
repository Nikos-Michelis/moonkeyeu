package com.moonkeyeu.core.api.security.controller;

import com.moonkeyeu.core.api.security.limiter.RateLimited;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("csrf")
public class CsrfController {

    @GetMapping("/token")
    @RateLimited(requests = 100, durationSeconds = 60)
    public CsrfToken csrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}