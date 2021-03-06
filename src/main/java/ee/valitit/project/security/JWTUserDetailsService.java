package ee.valitit.project.security;

import ee.valitit.project.domain.User;
import ee.valitit.project.exception.CustomException;
import ee.valitit.project.security.jwt.JWTUserFactory;
import ee.valitit.project.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Qualifier("JWTService")
@Data
@Service
public class JWTUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
             user = userService.getUser(username);
        } catch (CustomException exc) {
            throw new UsernameNotFoundException(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return JWTUserFactory.create(user);
        }
    }

}
