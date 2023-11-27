package com.example.insurance.service;

import com.example.insurance.entity.UserAccount;
import com.example.insurance.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository,@Lazy PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserAccount> getUserByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }


    public UserAccount signUpNewAccount(UserAccount userAccount)
    {
        String encodePassword = passwordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(encodePassword);
        return userAccountRepository.save(userAccount);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserAccount> userAccounts = userAccountRepository.findByEmail(email);
        String password;
        List<GrantedAuthority> authorities;
        if(userAccounts.isEmpty()) {
            throw new UsernameNotFoundException("Account does not exist with email = " + email);
        }
        email = userAccounts.get().getEmail();
        password = userAccounts.get().getPassword();
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userAccounts.get().getRole()));

        return new User(email,password,authorities);
    }


}
