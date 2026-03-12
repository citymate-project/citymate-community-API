package com.citymate.community.security;

import org.springframework.security.core.context.SecurityContextHolder;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SecurityUtils {

    public static UUID getCurrentUserId() {
        String principal = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        try {
            return UUID.fromString(principal);
        } catch (IllegalArgumentException e) {
            // Le subject du JWT est un username (ex: "alice"), pas un UUID
            // On génère un UUID déterministe basé sur le username
            return UUID.nameUUIDFromBytes(principal.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
    }

    public static String getCurrentUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(a -> a.replace("ROLE_", ""))
                .findFirst()
                .orElse("STUDENT");
    }
}