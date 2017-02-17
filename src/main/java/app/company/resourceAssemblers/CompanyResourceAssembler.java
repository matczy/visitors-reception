package app.company.resourceAssemblers;

import app.common.assembler.GenericAssembler;
import app.company.controller.CompanyController;
import app.company.model.Company;
import app.company.model.resource.CompanyResource;
import app.company.model.vo.CompanyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class CompanyResourceAssembler extends ResourceAssemblerSupport<Company, CompanyResource> {

    public CompanyResourceAssembler() {
        super(CompanyController.class, CompanyResource.class);
    }

    @Autowired
    private GenericAssembler<Company, CompanyVO> companyAssembler;

    public CompanyResource toResource(Company company) {
        CompanyResource resource = instantiateResource(company);
        resource.company = companyAssembler.toVO(company);
        resource.add(linkTo(CompanyController.class).slash(company.getId()).withSelfRel());
        return resource;
    }
}