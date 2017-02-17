package app.person.repository;


import app.person.model.Enums.PersonType;
import app.person.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "person", path = "person")
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(@Param("name") String name);

    @Query("from Person where active=true")
    List<Person> findAllActiveTrue();

    List<Person> findByTypeAndActiveTrue(@Param("personType") PersonType personType);

    Person findBySurnameAndNameAndDocumentIdentifierAndActiveTrue(String surname, String name, String documentIdentifier);
}