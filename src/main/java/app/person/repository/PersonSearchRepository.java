package app.person.repository;


import app.person.model.Enums.PersonType;
import app.person.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * User: Mateusz Czyrny
 * Date: 12/10/14
 * Time: 8:32 AM
 */
public interface PersonSearchRepository {

    List<Person> findBySearchText(String searchText, Optional<PersonType> personType) throws Exception;
     Page findBySearchText(String searchText, Optional<PersonType> personType, Pageable pageable) throws Exception;


    }
