package app.person.resourceAssemblers;

import app.common.assembler.GenericAssembler;
import app.common.assembler.IdNameAssembler;
import app.company.model.Company;
import app.company.model.vo.CompanyVO;
import app.company.repository.CompanyRepository;
import app.login.SecurityUtils;
import app.person.model.Enums.PersonType;
import app.person.model.entity.Person;
import app.person.model.vo.PersonVO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created with IntelliJ IDEA.
 * User: Dachu
 * Date: 10.07.15
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
@Component("personAssembler")
public class PersonAssemblerImpl implements GenericAssembler<Person, PersonVO> {

    private GenericAssembler<Company,CompanyVO> companyAssembler;

    private CompanyRepository companyRepository;

    private IdNameAssembler idNameAssembler;

    @Autowired
    public PersonAssemblerImpl(GenericAssembler<Company, CompanyVO> companyAssembler, CompanyRepository companyRepository, IdNameAssembler idNameAssembler) {
        this.companyAssembler = companyAssembler;
        this.companyRepository = companyRepository;
        this.idNameAssembler = idNameAssembler;
    }

    @Override
    public PersonVO toVO(Person person) {
        if(person!=null){
            PersonVO personVO = PersonVO.create();
            personVO.setName(person.getName());
            personVO.setSurname(person.getSurname());
            personVO.setId(person.getId());
            personVO.setAccess(person.getAccess());
            personVO.setPhone(person.getPhone());
            personVO.setEmail(person.getEmail());

            personVO.setDocumentIdentifier(person.getDocumentIdentifier());
            personVO.setConfirmEmailAvailable(person.getConfirmEmailAvailable());

            personVO.setType(idNameAssembler.toVO(person.getType().getId(), person.getType().getName()));
            if(person.getCompany()!=null){
                CompanyVO companyVO = companyAssembler.toVO(person.getCompany());
                personVO.setCompany(companyVO);
            }


            return personVO;
        }
        return null;

    }

    @Override
    @Transactional
    public Person toEntity(PersonVO personVO){
        if(personVO!=null){
            Person person = new Person();
            person.setCreatedBy(SecurityUtils.getCurrentLogin());
            person.setCreatedDate(DateTime.now());
            person.setName(personVO.getName());
            person.setSurname(personVO.getSurname());
            person.setId(personVO.getId());
            person.setAccess(true);
            person.setPhone(personVO.getPhone());
            person.setEmail(personVO.getEmail());
            person.setDocumentIdentifier(personVO.getDocumentIdentifier());
            person.setConfirmEmailAvailable(personVO.getConfirmEmailAvailable());
            if(personVO.getType()==null){
                person.setType(PersonType.VISITOR);
            }else{
                person.setType(PersonType.getById(personVO.getType().getId()));
            }
            if(!(personVO.getCompany().getId()==-1)){
                person.setCompany(companyRepository.findOne(personVO.getCompany().getId()));
            }else{
                Company company = companyRepository.findByNameAndActiveTrue(personVO.getCompany().getName());
                if(company==null){
                    company = new Company();
                    company.setName(personVO.getCompany().getName());
                    company.setCreatedBy(SecurityUtils.getCurrentLogin());
                    company.setCreatedDate(DateTime.now());
                    companyRepository.save(company);
                }

                person.setCompany(company);

            }

            return person;
        }else{
            return null;
        }


    }


}