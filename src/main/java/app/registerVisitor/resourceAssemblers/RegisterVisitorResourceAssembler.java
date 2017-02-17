package app.registerVisitor.resourceAssemblers;

import app.common.assembler.GenericAssembler;
import app.registerVisitor.controller.RegisterVisitorController;
import app.registerVisitor.model.RegisterVisitor;
import app.registerVisitor.model.RegisterVisitorResource;
import app.registerVisitor.model.RegisterVisitorVO;
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
public class RegisterVisitorResourceAssembler extends ResourceAssemblerSupport<RegisterVisitor, RegisterVisitorResource> {

    public RegisterVisitorResourceAssembler() {
        super(RegisterVisitorController.class, RegisterVisitorResource.class);
    }


    @Autowired
    private GenericAssembler<RegisterVisitor, RegisterVisitorVO> registerVisitorAssembler;

    public RegisterVisitorResource toResource(RegisterVisitor registerVisitor) {
        RegisterVisitorResource resource = instantiateResource(registerVisitor);
        resource.registerVisitor = registerVisitorAssembler.toVO(registerVisitor);
        resource.add(linkTo(RegisterVisitorController.class).slash(registerVisitor.getId()).withSelfRel());

        return resource;
    }

}