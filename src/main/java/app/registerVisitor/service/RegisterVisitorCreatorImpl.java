package app.registerVisitor.service;

import app.login.SecurityUtils;
import app.person.model.entity.Person;
import app.person.repository.PersonRepository;
import app.registerVisitor.model.DirectionType;
import app.registerVisitor.model.RegisterVisitor;
import app.registerVisitor.model.RegisterVisitorVO;
import app.visitCard.model.vo.VisitCardVO;
import app.visitCard.repository.VisitCardRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by matczy on 15.03.16.
 */
@Service
public class RegisterVisitorCreatorImpl implements RegisterVisitorCreator {

    private PersonRepository personRepository;
    private VisitCardRepository visitCardRepository;

    @Autowired
    public RegisterVisitorCreatorImpl(PersonRepository personRepository, VisitCardRepository visitCardRepository) {
        this.personRepository = personRepository;
        this.visitCardRepository = visitCardRepository;
    }

    @Override
    @Transactional
    public RegisterVisitor create(Person person, Person contactPerson, DirectionType directionType, DateTime entryDate, DateTime exitDate) {
        RegisterVisitor registerVisitor = new RegisterVisitor();
        registerVisitor.setPerson(person);
        registerVisitor.setContactPerson(contactPerson);
        registerVisitor.setEntryDate(entryDate);
        registerVisitor.setExitDate(exitDate);
        registerVisitor.setDirection(directionType);

        return registerVisitor;
    }

    @Override
    @Transactional
    //TODO pozastanawiac sie
    public RegisterVisitor create(RegisterVisitorVO registerVisitorVO, Person person) {
        RegisterVisitor registerVisitor = new RegisterVisitor();
        registerVisitor.setPerson(person);
        if(registerVisitorVO.getContactPerson()!=null){
            registerVisitor.setContactPerson(personRepository.findOne(registerVisitorVO.getContactPerson().getId()));

        }
        registerVisitor.setDirection(registerVisitorVO.getDirection());
        if(DirectionType.ENTRY == registerVisitorVO.getDirection()){
            registerVisitor.setEntryDate(DateTime.now());
            registerVisitor.setCreatedDate(DateTime.now());
            registerVisitor.setCreatedBy(SecurityUtils.getCurrentLogin());
        }else{
            registerVisitor.setExitDate(DateTime.now());
            registerVisitor.setActive(false);
            registerVisitor.setLastModifiedDate(DateTime.now());
            registerVisitor.setLastModifiedBy(SecurityUtils.getCurrentLogin());
        }

        return registerVisitor;
    }

    @Override
    public RegisterVisitor create(VisitCardVO visitCard, DirectionType directionType) {
        RegisterVisitor registerVisitor = new RegisterVisitor();
        registerVisitor.setPerson(personRepository.getOne(visitCard.getPerson().getId()));
        if(visitCard.getContactPerson()!=null){
            registerVisitor.setContactPerson(personRepository.findOne(visitCard.getContactPerson().getId()));

        }
        registerVisitor.setDirection(directionType);
        if(DirectionType.ENTRY == directionType){
            registerVisitor.setEntryDate(DateTime.now());
            registerVisitor.setCreatedDate(DateTime.now());
            registerVisitor.setCreatedBy(SecurityUtils.getCurrentLogin());
        }else{
            registerVisitor.setExitDate(DateTime.now());
            registerVisitor.setActive(false);
            registerVisitor.setLastModifiedDate(DateTime.now());
            registerVisitor.setLastModifiedBy(SecurityUtils.getCurrentLogin());
        }
        registerVisitor.setVisitCard(visitCardRepository.getOne(visitCard.getId()));

        return registerVisitor;    }
}
