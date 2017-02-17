package app.company.repository;


import app.company.model.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "company", path = "company")
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    @Query("from Company where active=true")
    List<Company> findAllActiveTrue();

    Company findByNameAndActiveTrue(String name);

}