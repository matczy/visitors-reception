package app.person.resourceAssemblers;

import app.common.assembler.GenericAssembler;
import app.person.controller.PersonController;
import app.person.model.entity.Person;
import app.person.model.resource.PersonResource;
import app.person.model.vo.PersonVO;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PersonResourceAssembler extends ResourceAssemblerSupport<Person, PersonResource> {

    public PersonResourceAssembler() {
        super(PersonController.class, PersonResource.class);
    }


    @Autowired
    private GenericAssembler<Person, PersonVO> personAssembler;

    public PersonResource toResource(Person person) {
        PersonResource resource = instantiateResource(person);
        resource.person = personAssembler.toVO(person);
        resource.add(linkTo(PersonController.class).slash(person.getId()).withSelfRel());
        return resource;
    }

}