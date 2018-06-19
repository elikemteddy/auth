package com.teasoft.auth.sec;

import com.teasoft.auth.model.Users;
import org.springframework.security.core.authority.AuthorityUtils;

public class TokenUser extends org.springframework.security.core.userdetails.User {

    private Users user;

    public TokenUser(Users user) {
        super(user.getId().toString(), user.getPassword(), user.getEnabled(), user.getAccountNonExpired(), user.getCredentialNonExpired(), user.getAccountNonLocked(), AuthorityUtils.createAuthorityList(user.getUserRoles()));
        this.user = user;
    }

    public Users getUser() {
        return user;
    }

    public String getId() {
        return user.getId().toString();
    }

    public String[] getRole() {
        return user.getUserRoles();
    }
}
