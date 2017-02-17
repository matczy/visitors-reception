package app.company.service;

import app.company.model.resource.CompanyResource;
import app.company.model.vo.CompanyVO;

import java.util.List;


public interface CompanyService {


    CompanyResource save(CompanyVO companyVO);

    List<CompanyResource> findAll();

    CompanyResource findOne(Long id);

    void delete(Long companyId);


    CompanyResource update(CompanyVO companyVO);
}
