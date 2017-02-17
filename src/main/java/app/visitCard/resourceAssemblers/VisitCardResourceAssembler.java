package app.visitCard.resourceAssemblers;

import app.common.assembler.GenericAssembler;
import app.visitCard.controller.VisitCardController;
import app.visitCard.model.VisitCard;
import app.visitCard.model.resource.VisitCardResource;
import app.visitCard.model.vo.VisitCardVO;
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
public class VisitCardResourceAssembler extends ResourceAssemblerSupport<VisitCard, VisitCardResource> {

    public VisitCardResourceAssembler() {
        super(VisitCardController.class, VisitCardResource.class);
    }

    @Autowired
    private GenericAssembler<VisitCard,VisitCardVO> visitCardAssembler;

    public VisitCardResource toResource(VisitCard visitCard) {
        VisitCardResource resource = instantiateResource(visitCard);
        resource.visitCard = visitCardAssembler.toVO(visitCard);
        resource.add(linkTo(VisitCardController.class).slash(visitCard.getId()).withSelfRel());
        return resource;
    }

}