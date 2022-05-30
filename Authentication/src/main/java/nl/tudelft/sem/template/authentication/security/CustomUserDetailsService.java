package nl.tudelft.sem.template.authentication.security;

import java.util.List;
import java.util.stream.Collectors;
import nl.tudelft.sem.template.authentication.entities.UserContainer;
import nl.tudelft.sem.template.authentication.service.RequestService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Class which loads core user-specific information.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final transient RequestService requestService;

    public CustomUserDetailsService(RequestService requestService) {
        this.requestService = requestService;
    }


    /**
     * Loads the details of user by username.
     *
     * @param username username.
     * @return User (security object).
     * @throws UsernameNotFoundException an exception.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserContainer userContainer = requestService
                .getByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username:" + username + " not found"));

        List<GrantedAuthority> grantedAuthorities = userContainer.getRole()
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(username, userContainer.getPassword(), grantedAuthorities);
    }
}
