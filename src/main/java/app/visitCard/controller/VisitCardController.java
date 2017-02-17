package app.visitCard.controller;

import app.visitCard.model.VisitCard;
import app.visitCard.model.resource.VisitCardResource;
import app.visitCard.model.vo.ManuallyPrintVisitCardVO;
import app.visitCard.model.vo.VisitCardVO;
import app.visitCard.resourceAssemblers.VisitCardResourceAssembler;
import app.visitCard.service.VisitCardService;
import app.visitRegister.model.TypeShowData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/visitCard")
public class VisitCardController {

    private VisitCardService visitCardService;
    private VisitCardResourceAssembler visitCardResourceAssembler;

    @Autowired
    public VisitCardController(VisitCardService visitCardService, VisitCardResourceAssembler visitCardResourceAssembler) {
        this.visitCardService = visitCardService;
        this.visitCardResourceAssembler = visitCardResourceAssembler;
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<Page> filterData(@RequestParam(value = "searchText") Optional<String> searchText, TypeShowData typeShowData, Integer page, Integer resultOnPage) throws Exception {
        return
                new ResponseEntity<>(visitCardService.filterData(searchText, typeShowData, page, resultOnPage), HttpStatus.OK);
    }

    @RequestMapping(value = "/suggestions", method = RequestMethod.GET)
    public ResponseEntity<List<VisitCardResource>> filterData(@RequestParam(value = "searchText") Optional<String> searchText) throws Exception {
        return
                new ResponseEntity<>(visitCardResourceAssembler.toResources(visitCardService.filterData(searchText)), HttpStatus.OK);
    }

    @RequestMapping(value = "/byNumber", method = RequestMethod.GET)
    public ResponseEntity<VisitCardResource> findByNumber(@RequestParam(value = "searchText") Optional<String> searchText) throws Exception {
        return
                new ResponseEntity<>(visitCardResourceAssembler.toResource(visitCardService.findByNumber(searchText)), HttpStatus.OK);
    }

    @RequestMapping(value = "/active/count", method = RequestMethod.GET)
    public ResponseEntity<Long> countActiveVisitCard() throws Exception {
        return  new ResponseEntity<>(visitCardService.countActiveVisitCard(), HttpStatus.OK);
    }
    @RequestMapping(value="/manually/print", method = RequestMethod.POST)
    public ResponseEntity<VisitCardResource> createVisitCard(@RequestBody ManuallyPrintVisitCardVO manuallyPrintVisitCardVO) throws Exception {
        VisitCard newVisitCard = visitCardService.createVisitCard(manuallyPrintVisitCardVO);
        return new ResponseEntity<>(visitCardResourceAssembler.toResource(newVisitCard), HttpStatus.CREATED);
    }


    @RequestMapping(value="/period/print", method = RequestMethod.POST)
    public ResponseEntity<VisitCardResource> createPeriodVisitCard(@RequestBody VisitCardVO visitCardVO) throws Exception {
        VisitCard newVisitCard = visitCardService.createPeriodVisitCard(visitCardVO);
        return new ResponseEntity<>(visitCardResourceAssembler.toResource(newVisitCard), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<VisitCardResource> delete(@PathVariable Optional<Long> id) throws Exception {
        if (id.isPresent()) {
            VisitCard visitCard = visitCardService.deactivate(id.get());
            return new ResponseEntity<>(visitCardResourceAssembler.toResource(visitCard),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @Scheduled(cron="0 0/1 * * * ?")
    public void updateVisitCardStatus() {
        System.out.println( "scheduler");
        visitCardService.updateVisitCardStatus();
    }



}

