package com.teasoft.auth.sec;


import com.teasoft.auth.model.Users;
import com.teasoft.auth.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UsersRepo userRepo;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public final TokenUser loadUserByUsername(String username) throws UsernameNotFoundException {
        final Users user = userRepo.findByUsername(username) == null ? userRepo.findByPhone(username) == null ? userRepo.findByEmail(username) == null ? null : userRepo.findByEmail(username) : userRepo.findByPhone(username) : userRepo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("");
        }
        TokenUser currentUser = new TokenUser(user);
        detailsChecker.check(currentUser);
        return currentUser;
    }
}