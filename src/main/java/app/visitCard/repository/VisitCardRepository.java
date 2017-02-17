package app.visitCard.repository;


import app.visitCard.model.VisitCard;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface VisitCardRepository extends JpaRepository<VisitCard, Long>,JpaSpecificationExecutor {

    List<VisitCard> findByActiveFalse();

    List<VisitCard> findByActiveTrue();

    Long countByActiveTrue();


    List<VisitCard> findByDateToBeforeAndManuallyDeactivatedFalse(DateTime dateTime);

    List<VisitCard> findByDateFromAfterAndManuallyDeactivatedFalse(DateTime dateTime);

    VisitCard findByNumber(String s);
}