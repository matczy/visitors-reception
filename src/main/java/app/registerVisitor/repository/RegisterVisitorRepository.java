package app.registerVisitor.repository;

import app.registerVisitor.model.RegisterVisitor;
import org.joda.time.DateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by matczy on 14.03.16.
 */
public interface RegisterVisitorRepository extends JpaRepository<RegisterVisitor, Long>, JpaSpecificationExecutor<RegisterVisitor> {

    Optional<RegisterVisitor> findByEntryDateNotNullAndExitDateNullAndPersonId(Long id);

    List<RegisterVisitor> findByEntryDateNotNullAndExitDateNotNull(Pageable topResult);

    List<RegisterVisitor> findByEntryDateNotNullAndExitDateNull(Pageable topResult);

    RegisterVisitor findByPersonIdAndEntryDateNotNullAndExitDateNull(Long personId);

    RegisterVisitor findByVisitCardIdAndEntryDateNotNullAndExitDateNull(Long visitCardId);

    Long countByActiveTrue();


    Long countByCreatedDateBetween(DateTime dateTime, DateTime now);

    @Query("select  SUBSTRING(rv.createdDate, 1, 10) , count(rv) from RegisterVisitor rv WHERE rv.createdDate BETWEEN :startDate AND :endDate group by SUBSTRING(rv.createdDate, 1, 10)")
    List countLast30DaysVisits(@Param(value = "startDate")DateTime startDate, @Param(value = "endDate")DateTime endDate);


    @Query("select  SUBSTRING(rv.createdDate, 1, 13) , count(rv) from RegisterVisitor rv WHERE rv.createdDate BETWEEN :startTime AND :endTime group by SUBSTRING(rv.createdDate, 1, 13)")
    List countLastDayByHourVisits(@Param(value = "startTime")DateTime startTime, @Param(value = "endTime")DateTime endTime);
}
