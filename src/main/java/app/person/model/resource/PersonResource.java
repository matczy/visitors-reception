package app.person.model.resource;

import app.person.model.vo.PersonVO;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created with IntelliJ IDEA.
 * User: Dachu
 * Date: 10.07.15
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class PersonResource extends ResourceSupport {

    public PersonVO person;

}