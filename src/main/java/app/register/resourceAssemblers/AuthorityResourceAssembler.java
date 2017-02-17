package app.register.resourceAssemblers;

import app.register.controller.AuthorityController;
import app.register.model.Authority;
import app.register.resource.AuthorityResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created with IntelliJ IDEA.
 * User: Dachu
 * Date: 10.07.15
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AuthorityResourceAssembler extends ResourceAssemblerSupport<Authority, AuthorityResource> {

    public AuthorityResourceAssembler() {
        super(AuthorityController.class, AuthorityResource.class);
    }

    public AuthorityResource toResource(Authority authority) {
        AuthorityResource resource = instantiateResource(authority);
        resource.authority = authority;
        resource.add(linkTo(AuthorityController.class).slash(authority.getName()).withSelfRel());
        return resource;
    }
}