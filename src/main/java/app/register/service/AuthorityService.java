package app.register.service;

import app.register.resource.AuthorityResource;

import java.util.List;


public interface AuthorityService {

    List<AuthorityResource> findAll();

}
