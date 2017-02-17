package app.register.controller;

import app.login.User;
import app.login.UserDTO;
import app.login.UserRepository;
import app.login.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class RegisterController {
    private final Logger log = LoggerFactory.getLogger(RegisterController.class);

    private UserRepository userRepository;

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /register -> register the user.
     */
    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity registerAccount(@Valid @RequestBody UserDTO userDTO) {
        return userRepository.findOneByLogin(userDTO.getLogin())
                .map(user -> new ResponseEntity("login already in use", HttpStatus.BAD_REQUEST))
                .orElseGet(() -> userRepository.findOneByEmail(userDTO.getEmail())
                                .map(user -> new ResponseEntity("e-mail address already in use", HttpStatus.BAD_REQUEST))
                                .orElseGet(() -> {
                                    User user = userService.createUserInformation(userDTO.getLogin(), userDTO.getPassword(), userDTO.getAuthorities(),
                                            userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail().toLowerCase(),
                                            userDTO.getLangKey());

                                    return new ResponseEntity(HttpStatus.CREATED);
                                })
                );
    }
}
