package app.company.service;

import app.common.assembler.GenericAssembler;
import app.company.model.Company;
import app.company.model.resource.CompanyResource;
import app.company.model.vo.CompanyVO;
import app.company.repository.CompanyRepository;
import app.company.resourceAssemblers.CompanyResourceAssembler;
import app.login.SecurityUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Mateusz Czyrny
 * Date: 12/10/14
 * Time: 10:15 AM
 */
@Service
public class CompanyServiceImpl implements CompanyService {


    private CompanyRepository companyRepository;

    private CompanyResourceAssembler companyResourceAssembler;

    private GenericAssembler<Company, CompanyVO> companyAssembler;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyResourceAssembler companyResourceAssembler, GenericAssembler<Company, CompanyVO> companyAssembler) {
        this.companyRepository = companyRepository;
        this.companyResourceAssembler = companyResourceAssembler;
        this.companyAssembler = companyAssembler;
    }

    @Override
    public CompanyResource save(CompanyVO companyVO) {
        return companyResourceAssembler.toResource(companyRepository.save(companyAssembler.toEntity(companyVO)));
    }

    @Override
    public List<CompanyResource> findAll() {
        return companyResourceAssembler.toResources(companyRepository.findAllActiveTrue());
    }

    @Override
    public CompanyResource findOne(Long id) {
        return companyResourceAssembler.toResource(companyRepository.findOne(id));
    }

    @Override
    public void delete(Long companyId) {
        Company company = companyRepository.findOne(companyId);
        company.setActive(false);

        companyRepository.save(company);
    }

    @Override
    public CompanyResource update(CompanyVO companyVO) {
        Company company =companyRepository.findOne(companyVO.getId());
        company.setName(companyVO.getName());
        company.setLastModifiedBy(SecurityUtils.getCurrentLogin());
        company.setLastModifiedDate(DateTime.now());

        return companyResourceAssembler.toResource(companyRepository.save(company));

    }

}
