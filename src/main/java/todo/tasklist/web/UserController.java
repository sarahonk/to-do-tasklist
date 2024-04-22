package todo.tasklist.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import todo.tasklist.model.Signup;
import todo.tasklist.model.User;
import todo.tasklist.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("signup", new Signup());
        return "registration.html";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("signup") Signup signup, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration.html";
        }

        String username = signup.getUsername();
        if (repository.findByUsername(username) != null) {
            bindingResult.rejectValue("username", "err.username", "Username already exists");
            return "registration.html";
        }

        String password = signup.getPassword();
        String passwordCheck = signup.getPasswordCheck();
        if (!password.equals(passwordCheck)) {
            bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords do not match");
            return "registration.html";
        }

        // Use the injected passwordEncoder bean to encode the password
        String encodedPassword = passwordEncoder.encode(password);

        User newUser = new User();
        newUser.setPassword(encodedPassword);
        newUser.setUsername(username);
        newUser.setRole("USER");

        // Save the user
        User savedUser = repository.save(newUser);

        // Log information about the saved user
        logger.info("User saved: {}", savedUser.getUsername());

        return "redirect:/login";
    }
}
