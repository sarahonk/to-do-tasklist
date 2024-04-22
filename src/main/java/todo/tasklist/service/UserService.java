package todo.tasklist.service;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import todo.tasklist.model.User;
import todo.tasklist.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Log the username being searched for
        logger.info("Searching for user with username: " + username);

        // Retrieve user details from the database
        User curruser = repository.findByUsername(username);

        // Log whether the user is found or not
        if (curruser != null) {
            logger.info("User found with username: " + username);
        } else {
            logger.info("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Log the retrieved user details
        logger.info("Retrieved user details: " + curruser);

        // Create UserDetails object for authentication
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                curruser.getUsername(), curruser.getPassword(),
                AuthorityUtils.createAuthorityList(curruser.getRole()));

        // Log authentication success
        logger.info("Authentication successful for user: " + username);

        return userDetails;
    }

    @Transactional
    public User saveUser(User user) {
        return repository.save(user);
    }
}
