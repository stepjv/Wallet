package com.wallet.services;

import com.wallet.models.User;
import com.wallet.repositories.UserRepository;
import com.wallet.util.IsExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public User reg(User user) {
        if(userIsExist(user)) {
            user.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
            userRepository.save(user);
            return user;
        }
        throw new IsExistException("User with this email is exist!");
    }

    public boolean userIsExist(User user) {
        return Optional.ofNullable(userRepository.findByEmail(user.getEmail())).isPresent();
    }

}


