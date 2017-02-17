package app.company.controller;

import app.company.model.resource.CompanyResource;
import app.company.model.vo.CompanyVO;
import app.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CompanyResource>> findAll() {
        return new ResponseEntity<>(companyService.findAll(), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CompanyResource> save(@RequestBody CompanyVO companyVO) throws Exception {
        return new ResponseEntity<>(companyService.save(companyVO), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CompanyResource> update(@RequestBody CompanyVO companyVO) throws Exception {
        return new ResponseEntity<>(companyService.update(companyVO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CompanyResource> delete(@PathVariable Optional<Long> id) throws Exception {
        if (id.isPresent()) {
            companyService.delete(id.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CompanyResource> findOne(@PathVariable Optional<Long> id) throws Exception {
        if (id.isPresent()) {

            return new ResponseEntity<>(companyService.findOne(id.get()),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

