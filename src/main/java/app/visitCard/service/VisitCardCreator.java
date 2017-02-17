package app.visitCard.service;

import app.person.model.entity.Person;
import app.visitCard.model.VisitCard;
import app.visitCard.model.vo.VisitCardVO;
import org.joda.time.DateTime;

/**
 * Created by matczy on 14.03.16.
 */
public interface VisitCardCreator {
    VisitCard createOneDay(Person person, Person contactPerson);

    VisitCard create(Person person, Person contactPerson, int numberOfDays);

    VisitCard create(VisitCardVO visitCardVO);

    VisitCard create(Person person, Person contactPerson, DateTime dateFrom, DateTime dateTo);

}
