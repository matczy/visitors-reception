package app.person.service;

import app.person.model.Enums.PersonType;
import app.person.model.resource.PersonResource;
import app.person.model.vo.PersonVO;
import app.visitRegister.model.TypeShowData;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface PersonService {

    List<PersonResource> findBySearchText(Optional<String> searchText) throws Exception;

    List<PersonResource> findContactPersonBySearchText(Optional<String> searchText) throws Exception;
//
//    List<PersonResource> findAllPersonsOnObject();
//
    List<PersonType> findAllPersonTypes();

    PersonResource save(PersonVO personVO);

    PersonResource update(PersonVO personVO);

    void delete(Long personId);

    List<PersonResource> findAll(Optional<Long> personType);

    PersonResource findOne(Long id);

    PersonResource changeConfirmEmailBehaviour(Long personId, boolean confirmEmail);

    Page filterData(Optional<String> searchText, TypeShowData typeShowData, Integer page, Integer resultOnPage) throws Exception;

}
