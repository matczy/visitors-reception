package app.visitCard.service;

import app.visitCard.model.VisitCard;
import app.visitCard.model.vo.ManuallyPrintVisitCardVO;
import app.visitCard.model.vo.VisitCardVO;
import app.visitRegister.model.TypeShowData;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface VisitCardService  {
   List findAllActiveForPerson(Long id);

//    VisitCard save(VisitCardVO visitCard);

    void updateVisitCardStatus();

    void disableAllVisitCardForPerson(Long personId);

    VisitCard createVisitCard(ManuallyPrintVisitCardVO manuallyPrintVisitCardVO);

    VisitCard createPeriodVisitCard(VisitCardVO visitCardVO);


    VisitCard deactivate(Long id);

    Page filterData(Optional<String> searchText, TypeShowData typeShowData, Integer page, Integer resultOnPage) throws Exception;

    Long countActiveVisitCard();

    List<VisitCard> filterData(Optional<String> searchText);

    VisitCard findByNumber(Optional<String> searchText);
}

