package app.visitCard.repository.Specyfications;

//import app.company.model.Company_;
//import app.company.repository.CompanyRepository;

import app.person.model.entity.Person_;
import app.visitCard.model.VisitCard;
import app.visitCard.model.VisitCard_;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VisitCardSpecification {



    public static Specification<VisitCard> findAllActiveForPerson(final Long id) {
        return new Specification<VisitCard>() {
            @Override
            public Predicate toPredicate(Root<VisitCard> VisitCardRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();
                predicates.add(cb.equal(VisitCardRoot.get(VisitCard_.person).get(Person_.id), id));
                predicates.add(cb.equal(VisitCardRoot.get(VisitCard_.active), true));
//                predicates.add(cb.ge(VisitCardRoot.get(VisitCard_.dateFrom),DateTime.now()));
//                predicates.add(cb.le(VisitCardRoot.get(VisitCard_.dateTo),DateTime.now()));

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    public static Specification<VisitCard> findAllForUpdate() {
        return new Specification<VisitCard>() {
            @Override
            public Predicate toPredicate(Root<VisitCard> VisitCardRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();
                predicates.add(cb.equal(VisitCardRoot.get(VisitCard_.active), true));
                predicates.add(cb.lessThan(VisitCardRoot.get(VisitCard_.dateTo), DateTime.now()));

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    private static Date convertToDate(final String dateString) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return df.parse(dateString);
        } catch (ParseException e) {

        }

        return null;
    }


}

