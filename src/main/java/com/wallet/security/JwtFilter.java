package com.wallet.security;

import com.wallet.models.ProfileEntity;
import com.wallet.models.UserEntity;
import com.wallet.repositories.ProfileRepository;
import com.wallet.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // берем токен с header запроса
        if (token != null && !token.isEmpty()) {
            final UserEntity user = userRepository.findUserByToken(token);
            if (user == null) {
                // Можно отправить 401, а не бросать исключение
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }

            final ProfileEntity profile = profileRepository.findProfileByUserId(user.getId());
            setInfoToAuthContext(user, profile);
        }

        filterChain.doFilter(request, response); // пускает фильтр дальше
    }

    private void setInfoToAuthContext(UserEntity user, ProfileEntity profile) {
        final Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        final AuthProfileContext userDetails =
                AuthProfileContext.init(authorities, user, profile);

        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
