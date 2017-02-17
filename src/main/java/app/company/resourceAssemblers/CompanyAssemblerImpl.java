package app.company.resourceAssemblers;

import app.common.assembler.GenericAssembler;
import app.company.model.Company;
import app.company.model.vo.CompanyVO;
import app.login.SecurityUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component("companyAssembler")
public class CompanyAssemblerImpl implements GenericAssembler<Company, CompanyVO> {

    @Override
    public CompanyVO toVO(Company company) {
        return CompanyVO.create(company.getId(),company.getName());
    }

    @Override
    public Company toEntity(CompanyVO companyVO) {
        Company company = new Company();
        company.setCreatedBy(SecurityUtils.getCurrentLogin());
        company.setCreatedDate(DateTime.now());
        company.setName(companyVO.getName());

        return company;
    }

}