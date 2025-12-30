package com.wallet.security;

import com.wallet.util.exceptions.NoAuthException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    private static AuthProfileContext getCurrent() {
        return (AuthProfileContext) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public static int getProfileId() {
        final AuthProfileContext context = getCurrent();
        if (context == null) {
            throw new NoAuthException("No auth token");
        }
        return context.getProfileId();
    }
}
