package app.visitCard.model.assembler;

import app.common.assembler.GenericAssembler;
import app.person.model.entity.Person;
import app.person.model.vo.PersonVO;
import app.visitCard.model.VisitCard;
import app.visitCard.model.vo.VisitCardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Dachu on 2015-11-19.
 */
@Component("visitCardAssembler")
public class VisitCardAssemblerImpl implements GenericAssembler<VisitCard,VisitCardVO> {

    private GenericAssembler<Person,PersonVO> personAssembler;

    @Autowired
    public VisitCardAssemblerImpl(GenericAssembler<Person,PersonVO>  personAssembler) {
        this.personAssembler = personAssembler;
    }

    @Override
    public VisitCardVO toVO(VisitCard visitCard) {
        VisitCardVO visitCardVO = VisitCardVO.create();
        visitCardVO.setId(visitCard.getId());
        visitCardVO.setNumber(visitCard.getNumber());
        visitCardVO.setPerson(personAssembler.toVO(visitCard.getPerson()));
        if(visitCard.getContactPerson()!=null){
            visitCardVO.setContactPerson(personAssembler.toVO(visitCard.getContactPerson()));
        }
        visitCardVO.setDateFrom(visitCard.getDateFrom());
        visitCardVO.setDateTo(visitCard.getDateTo());
        visitCardVO.setActive(visitCard.getActive());
        visitCardVO.setCreatedDate(visitCard.getCreatedDate());
        visitCardVO.setCreatedBy(visitCard.getCreatedBy());

        return visitCardVO;
    }

    @Override
    public VisitCard toEntity(VisitCardVO visitCardVO) {
        VisitCard visitCard = new VisitCard();
        visitCard.setId(visitCardVO.getId());
        visitCard.setNumber(visitCardVO.getNumber());
        visitCard.setPerson(personAssembler.toEntity(visitCardVO.getPerson()));
        visitCard.setContactPerson(personAssembler.toEntity(visitCardVO.getContactPerson()));
        visitCard.setDateFrom(visitCardVO.getDateFrom());
        visitCard.setDateTo(visitCardVO.getDateTo());
        visitCard.setActive(true);

        return null;
    }


}
