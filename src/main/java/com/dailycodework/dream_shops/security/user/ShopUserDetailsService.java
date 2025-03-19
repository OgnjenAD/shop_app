package com.dailycodework.dream_shops.security.user;

import com.dailycodework.dream_shops.model.Users;
import com.dailycodework.dream_shops.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShopUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = Optional.ofNullable(usersRepository.findUsersByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
        return ShopUserDetails.buildUserDetails(user);
    }
}
