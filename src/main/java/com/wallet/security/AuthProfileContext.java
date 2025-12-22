package com.wallet.security;

import com.wallet.models.ProfileEntity;
import com.wallet.models.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthProfileContext extends User {

    private int userId;
    private int profileId;
    private String email;

    public static AuthProfileContext init(Collection<? extends GrantedAuthority> authorities,
                                          UserEntity user, ProfileEntity profile) {
        return new AuthProfileContext(authorities, user, profile);
    }


    private AuthProfileContext(Collection<? extends GrantedAuthority> authorities,
                               UserEntity user, ProfileEntity profile) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.userId = user.getId();
        this.profileId = profile.getId();
        this.email = user.getEmail();
    }


    public AuthProfileContext(String username, String password,
                              Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthProfileContext(String username, String password,
                              boolean enabled, boolean accountNonExpired,
                              boolean credentialsNonExpired, boolean accountNonLocked,
                              Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
