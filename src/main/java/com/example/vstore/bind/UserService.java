package com.example.vstore.bind;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserServer userServer;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        final Optional<User> optionalUser = userServer.findByUser_name(userName);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else {
            throw new UsernameNotFoundException(MessageFormat.format("User with user name {0} cannot be found.", userName));
        }
    }

    public void signUpUser(User user) {

        final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(true);
        final User createdUser = userServer.save(user);

    }
}
