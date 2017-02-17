package app.registerVisitor.service;

import app.person.model.entity.Person;
import app.registerVisitor.model.DirectionType;
import app.registerVisitor.model.RegisterVisitor;
import app.registerVisitor.model.RegisterVisitorVO;
import app.visitCard.model.vo.VisitCardVO;
import org.joda.time.DateTime;

/**
 * Created by matczy on 15.03.16.
 */
public interface RegisterVisitorCreator {
    RegisterVisitor create(Person person, Person contactPerson, DirectionType directionType, DateTime entryDate, DateTime exitDate);

    RegisterVisitor create(RegisterVisitorVO registerVisitorVO, Person person);

    RegisterVisitor create(VisitCardVO visitCard, DirectionType directionType);
}
