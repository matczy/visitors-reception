package app.registerVisitor.resourceAssemblers;

import app.common.assembler.GenericAssembler;
import app.person.model.entity.Person;
import app.person.model.vo.PersonVO;
import app.registerVisitor.model.DirectionType;
import app.registerVisitor.model.RegisterVisitor;
import app.registerVisitor.model.RegisterVisitorVO;
import app.visitCard.model.VisitCard;
import app.visitCard.model.vo.VisitCardVO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Dachu
 * Date: 10.07.15
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
@Component("registerVisitorAssembler")
public class RegisterVisitorAssemblerImpl implements GenericAssembler<RegisterVisitor, RegisterVisitorVO> {

    private GenericAssembler<Person, PersonVO> personAssembler;
    private GenericAssembler<VisitCard,VisitCardVO> visitCardAssembler;

    @Autowired
    public RegisterVisitorAssemblerImpl(GenericAssembler<Person, PersonVO>  personAssembler, GenericAssembler<VisitCard,VisitCardVO> visitCardAssembler) {
        this.personAssembler = personAssembler;
        this.visitCardAssembler = visitCardAssembler;
    }

    @Override
    public RegisterVisitorVO toVO(RegisterVisitor registerVisitor) {
        if(registerVisitor!=null){

            PersonVO personVO = personAssembler.toVO(registerVisitor.getPerson());
            PersonVO contactPersonVO = personAssembler.toVO(registerVisitor.getContactPerson());
            VisitCardVO visitCardVO = visitCardAssembler.toVO(registerVisitor.getVisitCard());
            DateTime entryDate = registerVisitor.getEntryDate();
            DateTime exitDateDate = registerVisitor.getExitDate();
            DirectionType direction = registerVisitor.getDirection();

            RegisterVisitorVO registerVisitorVO = new RegisterVisitorVO();
            registerVisitorVO.setPerson(personVO);
            registerVisitorVO.setContactPerson(contactPersonVO);
            registerVisitorVO.setVisitCard(visitCardVO);
            registerVisitorVO.setEntryDate(entryDate);
            registerVisitorVO.setExitDate(exitDateDate);
            registerVisitorVO.setDirection(direction);
            registerVisitorVO.setId(registerVisitor.getId());
            registerVisitorVO.setComment(registerVisitor.getComment());


            return registerVisitorVO;
        }
        return null;

    }

    @Override
    @Transactional
    public RegisterVisitor toEntity(RegisterVisitorVO registerVisitorVO){
       if(registerVisitorVO!=null){
           Person person = personAssembler.toEntity(registerVisitorVO.getPerson());
           Person contactPerson = personAssembler.toEntity(registerVisitorVO.getContactPerson());
           VisitCard visitCard = visitCardAssembler.toEntity(registerVisitorVO.getVisitCard());
           DateTime entryDate = registerVisitorVO.getEntryDate();
           DateTime exitDateDate = registerVisitorVO.getExitDate();
           DirectionType direction = registerVisitorVO.getDirection();

           RegisterVisitor registerVisitor = new RegisterVisitor();
           registerVisitor.setPerson(person);
           registerVisitor.setContactPerson(contactPerson);
           registerVisitor.setVisitCard(visitCard);
           registerVisitor.setEntryDate(entryDate);
           registerVisitor.setExitDate(exitDateDate);
           registerVisitor.setDirection(direction);
           registerVisitor.setActive(registerVisitorVO.getActive());

       }
        return null;
    }


}