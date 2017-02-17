package app.register.service;

import app.register.repository.AuthorityRepository;
import app.register.resource.AuthorityResource;
import app.register.resourceAssemblers.AuthorityResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Mateusz Czyrny
 * Date: 12/10/14
 * Time: 10:15 AM
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {


    private AuthorityRepository authorityRepository;

    private AuthorityResourceAssembler profileResourceAssembler;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository, AuthorityResourceAssembler profileResourceAssembler) {
        this.authorityRepository = authorityRepository;
        this.profileResourceAssembler = profileResourceAssembler;
    }

    @Override
    public List<AuthorityResource> findAll() {
        return profileResourceAssembler.toResources(authorityRepository.findAll());
    }

}
