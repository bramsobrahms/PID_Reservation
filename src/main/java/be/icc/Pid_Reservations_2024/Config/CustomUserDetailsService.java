package be.icc.Pid_Reservations_2024.Config;

import be.icc.Pid_Reservations_2024.Models.Users;
import be.icc.Pid_Reservations_2024.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom implementation of {@link UserDetailsService} to load user-specific data
 * for authentication and authorization purposes.
 *
 * This service retrieves a user from the {@link UserRepository} by their login
 * and returns a {@link UserDetails} object containing the user's login, password,
 * and granted authorities based on their role.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                getGrantedAuthorities(user.getRole().toString())
        );

    }

    /**
     * Converts a user's role into a list of {@link GrantedAuthority} objects.
     *
     * @param role the user's role
     * @return a list of {@link GrantedAuthority} representing the user's role
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

}
