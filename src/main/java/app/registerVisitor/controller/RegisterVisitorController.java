package app.registerVisitor.controller;

import app.registerVisitor.model.RegisterVisitor;
import app.registerVisitor.model.RegisterVisitorByVisitCardWrapper;
import app.registerVisitor.model.RegisterVisitorResource;
import app.registerVisitor.model.RegisterVisitorVO;
import app.registerVisitor.resourceAssemblers.RegisterVisitorResourceAssembler;
import app.registerVisitor.service.RegisterVisitorService;
import app.visitRegister.model.TypeShowData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by matczy on 14.03.16.
 */

@RestController
@RequestMapping(value = "/register/visitor")
public class RegisterVisitorController {

    private RegisterVisitorService registerVisitorService;


    private RegisterVisitorResourceAssembler registerVisitorResourceAssembler;

    @Autowired
    public RegisterVisitorController(RegisterVisitorService registerVisitorService, RegisterVisitorResourceAssembler registerVisitorResourceAssembler) {
        this.registerVisitorService = registerVisitorService;
        this.registerVisitorResourceAssembler = registerVisitorResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterVisitorResource> save(@RequestBody RegisterVisitorVO registerVisitorVO) throws Exception {

           RegisterVisitor registerVisitor = registerVisitorService.registerVisitorAction(registerVisitorVO);



           return new ResponseEntity<>(registerVisitorResourceAssembler.toResource(registerVisitor), HttpStatus.CREATED);
    }

    @RequestMapping(value="/exit" ,method = RequestMethod.POST)
    public ResponseEntity<RegisterVisitorResource> save(@RequestBody Long id) {

        RegisterVisitor registerVisitor = registerVisitorService.registerVisitorExitAction(id);

        return new ResponseEntity<>(registerVisitorResourceAssembler.toResource(registerVisitor), HttpStatus.CREATED);
    }

    @RequestMapping(value="/multi",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegisterVisitorResource>> saveMulti(@RequestBody List<RegisterVisitorVO> registerVisitorsVO) throws Exception {

        List<RegisterVisitor> registerVisitors = registerVisitorService.registerMultiVisitorAction(registerVisitorsVO);

        return new ResponseEntity<>(registerVisitorResourceAssembler.toResources(registerVisitors), HttpStatus.CREATED);
    }

    @RequestMapping(value="/byVisitCard",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterVisitorResource> saveByVisitCard(@RequestBody RegisterVisitorByVisitCardWrapper registerVisitorByVisitCardWrapper) throws Exception {

        RegisterVisitor registerVisitor = registerVisitorService.registerVisitorAction(registerVisitorByVisitCardWrapper);

        return new ResponseEntity<>(registerVisitorResourceAssembler.toResource(registerVisitor), HttpStatus.CREATED);
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<Page> filterData(@RequestParam(value = "searchText") Optional<String> searchText, TypeShowData typeShowData, Integer page, Integer resultOnPage) throws Exception {
        return
                new ResponseEntity<>(registerVisitorService.filterData(searchText, typeShowData, page, resultOnPage), HttpStatus.OK);
    }


    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseEntity<Long> countAllVisitors() {
        return new ResponseEntity<>(registerVisitorService.countAllVisitors(), HttpStatus.OK);
    }

    @RequestMapping(value = "/onObject/count", method = RequestMethod.GET)
    public ResponseEntity<Long> countAllVisitorsOnObject() {
        return new ResponseEntity<>(registerVisitorService.countAllVisitorsOnObject(), HttpStatus.OK);
    }

    @RequestMapping(value = "/today/count", method = RequestMethod.GET)
    public ResponseEntity<Long> countTodayVisits() {
        return new ResponseEntity<>(registerVisitorService.countTodayVisits(), HttpStatus.OK);
    }

    @RequestMapping(value = "/last/30/days/visits/count", method = RequestMethod.GET)
    public ResponseEntity<List> countLast30DaysVisits() {
        return new ResponseEntity<>(registerVisitorService.countLast30DaysVisits(), HttpStatus.OK);
    }

    @RequestMapping(value = "/last/1/days/byHour/visits/count", method = RequestMethod.GET)
    public ResponseEntity<List> countLastDayByHourVisits() {
        return new ResponseEntity<>(registerVisitorService.countLastDayByHourVisits(), HttpStatus.OK);
    }

    @RequestMapping(value = "/last/30/days", method = RequestMethod.GET)
    public ResponseEntity<List> getLast30DaysLabel() {

        return new ResponseEntity<>(registerVisitorService.getLast30DaysLabel(), HttpStatus.OK);
    }


    @RequestMapping(value = "/last/1/days/byHour", method = RequestMethod.GET)
    public ResponseEntity<List> getLastDayByHourLabel() {

        return new ResponseEntity<>(registerVisitorService.getLastDayByHourLabel(), HttpStatus.OK);
    }

}