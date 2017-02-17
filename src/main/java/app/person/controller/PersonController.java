package app.person.controller;

import app.person.model.Enums.PersonType;
import app.person.model.resource.PersonResource;
import app.person.model.vo.PersonVO;
import app.person.service.PersonService;
import app.visitRegister.model.TypeShowData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//TODO zmienic aby resource byl tu tworzony a nie w serwisie
@RestController
@RequestMapping(value = "/person")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PersonResource>> findAll(@RequestParam(value = "personType") Optional<Long> personType) throws Exception {
        return new ResponseEntity<>(personService.findAll(personType), HttpStatus.OK);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public ResponseEntity<Page> filterData(@RequestParam(value = "searchText") Optional<String> searchText, TypeShowData typeShowData, Integer page, Integer resultOnPage) throws Exception {
        return
                new ResponseEntity<>(personService.filterData(searchText, typeShowData, page, resultOnPage), HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<PersonResource>> findPersonBySearchText(@RequestParam(value = "searchText") Optional<String> searchText) throws Exception {
        return new ResponseEntity<>(personService.findBySearchText(searchText), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PersonResource> findOne(@PathVariable Optional<Long> id) throws Exception {
        if (id.isPresent()) {
            return new ResponseEntity<>( personService.findOne(id.get()),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @RequestMapping(value = "/search/contactPerson", method = RequestMethod.GET)
    public ResponseEntity<List<PersonResource>> findContactPersonBySearchText(@RequestParam(value = "searchText") Optional<String> searchText) throws Exception {
        return new ResponseEntity<>(personService.findContactPersonBySearchText(searchText), HttpStatus.OK);
    }

    @RequestMapping(value = "/type/search", method = RequestMethod.GET)
    public ResponseEntity<List<PersonType>> findAllPersonTypes() throws Exception {
        return new ResponseEntity<>(personService.findAllPersonTypes(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PersonResource> save(@RequestBody PersonVO person) throws Exception {
        return new ResponseEntity<>(personService.save(person), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<PersonResource> update(@RequestBody PersonVO person) throws Exception {
        return new ResponseEntity<>(personService.update(person), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{personId}/confirmationEmail" , method = RequestMethod.GET)
    public ResponseEntity<PersonResource> changeConfirmEmailBehaviour(@PathVariable Long personId, @RequestParam(value = "confirmEmail") Boolean confirmEmail) throws Exception {
        return new ResponseEntity<>(personService.changeConfirmEmailBehaviour(personId, confirmEmail), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<PersonResource> delete(@PathVariable Optional<Long> id) throws Exception {
        if (id.isPresent()) {
            personService.delete(id.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

