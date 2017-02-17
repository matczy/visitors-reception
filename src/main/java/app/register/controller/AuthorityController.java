package app.register.controller;

import app.register.resource.AuthorityResource;
import app.register.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/profile")
public class AuthorityController {

    private AuthorityService profileService;

    @Autowired
    public AuthorityController(AuthorityService companyService) {
        this.profileService = companyService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AuthorityResource>> findAll() {
        return new ResponseEntity<>(profileService.findAll(), HttpStatus.OK);
    }



}

