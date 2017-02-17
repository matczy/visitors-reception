package app.person.service;

import app.common.assembler.GenericAssembler;
import app.company.model.Company;
import app.company.repository.CompanyRepository;
import app.login.SecurityUtils;
import app.person.model.Enums.PersonType;
import app.person.model.entity.Person;
import app.person.model.resource.PersonResource;
import app.person.model.vo.PersonVO;
import app.person.repository.PersonRepository;
import app.person.repository.PersonSearchRepository;
import app.person.resourceAssemblers.PersonResourceAssembler;
import app.visitRegister.model.TypeShowData;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * User: Mateusz Czyrny
 * Date: 12/10/14
 * Time: 10:15 AM
 */
@Service
public class PersonServiceImpl implements PersonService {

    private PersonSearchRepository personSearchRepository;

    private PersonRepository personRepository;

    private CompanyRepository companyRepository;

    private PersonResourceAssembler personResourceAssembler;

    private GenericAssembler<Person,PersonVO> personAssembler;

    @Autowired
    public PersonServiceImpl(PersonSearchRepository personSearchRepository, PersonRepository personRepository, CompanyRepository companyRepository, PersonResourceAssembler personResourceAssembler, GenericAssembler<Person,PersonVO>  personAssembler) {
        this.personSearchRepository = personSearchRepository;
        this.personRepository = personRepository;
        this.companyRepository = companyRepository;
        this.personResourceAssembler = personResourceAssembler;
        this.personAssembler = personAssembler;
    }


    @Override
    @Transactional
    public List<PersonResource> findBySearchText(Optional<String> searchText) throws Exception {
        final Iterable<Person> persons =
                searchText.isPresent() ? personSearchRepository.findBySearchText(searchText.get(), Optional.empty())
                        : personRepository.findAll();


        return personResourceAssembler.toResources(persons);
    }

    @Override
    @Transactional
    public List<PersonResource> findContactPersonBySearchText(Optional<String> searchText) throws Exception {
        final String searchTextValue = searchText.orElseGet(() -> "");
        final Iterable<Person> persons = personSearchRepository.findBySearchText(searchTextValue, Optional.of(PersonType.WORKER));

        return personResourceAssembler.toResources(persons);
    }

    @Override
    public List<PersonType> findAllPersonTypes() {
        return Arrays.asList(PersonType.values());
    }

    @Override
    @Transactional
    public PersonResource save(PersonVO personVO) {
        return personResourceAssembler.toResource(personRepository.save(personAssembler.toEntity(personVO)));
    }

    @Override
    @Transactional
    public PersonResource update(PersonVO personVO) {
        Person personToUpdate = personRepository.findOne(personVO.getId());
        if(personVO.getCompany().getId()!=null){
            Company company = companyRepository.findOne(personVO.getCompany().getId());
            personToUpdate.setCompany(company);
        }

        personToUpdate.setSurname(personVO.getSurname());
        personToUpdate.setName(personVO.getName());
        personToUpdate.setPhone(personVO.getPhone());
        personToUpdate.setType(PersonType.getById(personVO.getType().getId()));
        personToUpdate.setLastModifiedBy(SecurityUtils.getCurrentLogin());
        personToUpdate.setLastModifiedDate(DateTime.now());
        personToUpdate.setDocumentIdentifier(personVO.getDocumentIdentifier());
        personToUpdate.setEmail(personVO.getEmail());
        personToUpdate.setConfirmEmailAvailable(personVO.getConfirmEmailAvailable());

        return personResourceAssembler.toResource(personRepository.save(personToUpdate));
    }

    @Override
    public void delete(Long personId) {
        Person person = personRepository.findOne(personId);
        person.setActive(false);
        personRepository.save(person);
    }

    @Override
    public List<PersonResource> findAll(Optional<Long> personType) {
        if(personType.isPresent()){
             return personResourceAssembler.toResources(personRepository.findByTypeAndActiveTrue(PersonType.getById(personType.get())));

        }else{
              return personResourceAssembler.toResources(personRepository.findAllActiveTrue());
        }
    }

    @Override
    public PersonResource findOne(Long id) {
       return  personResourceAssembler.toResource(personRepository.findOne(id));
    }

    @Override
    public PersonResource changeConfirmEmailBehaviour(Long personId, boolean confirmEmail) {
        Person person = personRepository.findOne(personId);
        person.setConfirmEmailAvailable(confirmEmail);
        return personResourceAssembler.toResource(personRepository.save(person));
    }

    @Override
    @javax.transaction.Transactional
    public Page filterData(Optional<String> searchText, TypeShowData typeShowData, Integer page, Integer resultOnPage) throws Exception {
            Pageable pageable = new PageRequest(page,resultOnPage);
            Optional<PersonType> optionalPersonType = Optional.empty();
            if(TypeShowData.WORKER == typeShowData){
                optionalPersonType = Optional.of(PersonType.WORKER);
            }else if(TypeShowData.GUEST == typeShowData){
                optionalPersonType = Optional.of(PersonType.VISITOR);
            }
            if(searchText.isPresent() ){
                return personSearchRepository.findBySearchText(searchText.get(), optionalPersonType, pageable);
            }else{
                return  personSearchRepository.findBySearchText(org.apache.commons.lang3.StringUtils.EMPTY, optionalPersonType, pageable);
            }

    }

}
