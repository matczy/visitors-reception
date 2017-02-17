package app.visitCard.service;

import app.login.SecurityUtils;
import app.person.model.entity.Person;
import app.person.repository.PersonRepository;
import app.visitCard.model.VisitCard;
import app.visitCard.model.vo.VisitCardVO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static app.common.TimeUtils.checkIsActive;

/**
 * Created by matczy on 14.03.16.
 */
@Service
public class VisitCardCreatorImpl implements VisitCardCreator  {


    private PersonRepository personRepository;

    @Autowired
    public VisitCardCreatorImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public VisitCard createOneDay(Person person, Person contactPerson) {
        VisitCard visitCard = new VisitCard();
        visitCard.setPerson(person);
        visitCard.setContactPerson(contactPerson);
        visitCard.setDateFrom(DateTime.now());
        visitCard.setDateTo(DateTime.now().plusDays(1).withTimeAtStartOfDay().minusMinutes(1));
        visitCard.setActive(checkIsActive(visitCard.getDateFrom(), visitCard.getDateTo()));
        visitCard.setCreatedDate(DateTime.now());
        visitCard.setCreatedBy(SecurityUtils.getCurrentLogin());
        visitCard.setNumber("V"+visitCard.getId());

        return visitCard;
    }

    @Override
    public VisitCard create(Person person, Person contactPerson, int numberOfDays) {
        VisitCard visitCard = new VisitCard();
        visitCard.setPerson(person);
        visitCard.setContactPerson(contactPerson);
        visitCard.setDateFrom(DateTime.now());
        visitCard.setDateTo(DateTime.now().plusDays(numberOfDays).withTimeAtStartOfDay().minusMinutes(1));
        visitCard.setActive(checkIsActive(visitCard.getDateFrom(), visitCard.getDateTo()));
        visitCard.setCreatedDate(DateTime.now());
        visitCard.setCreatedBy(SecurityUtils.getCurrentLogin());
        visitCard.setNumber("V"+visitCard.getId());
        return visitCard;
    }

    @Override
    public VisitCard create(VisitCardVO visitCardVO) {
        VisitCard visitCard = new VisitCard();
        visitCard.setPerson(personRepository.findOne(visitCardVO.getPerson().getId()));
        if(visitCardVO.getContactPerson()!=null && visitCardVO.getContactPerson().getId()!=null){
            visitCard.setContactPerson(personRepository.findOne(visitCardVO.getContactPerson().getId()));
        }
        visitCard.setDateFrom(visitCardVO.getDateFrom());
        visitCard.setDateTo(visitCardVO.getDateTo().plusDays(1).minusMinutes(1));
        visitCard.setActive(checkIsActive(visitCard.getDateFrom(), visitCard.getDateTo()));
        visitCard.setCreatedDate(DateTime.now());
        visitCard.setCreatedBy(SecurityUtils.getCurrentLogin());
        visitCard.setNumber("V"+visitCard.getId());


        return visitCard;
    }

    @Override
    public VisitCard create(Person person, Person contactPerson, DateTime dateFrom, DateTime dateTo) {
        VisitCard visitCard = new VisitCard();
        visitCard.setPerson(person);
        visitCard.setContactPerson(contactPerson);
        visitCard.setDateFrom(dateFrom);
        visitCard.setDateTo(dateTo);
        visitCard.setActive(checkIsActive(visitCard.getDateFrom(), visitCard.getDateTo()));
        visitCard.setCreatedDate(DateTime.now());
        visitCard.setCreatedBy(SecurityUtils.getCurrentLogin());

        visitCard.setNumber("V"+visitCard.getId());


        return visitCard;

    }


}
