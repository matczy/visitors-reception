package app.visitCard.repository;

import app.visitCard.model.VisitCard;
import app.visitRegister.model.TypeShowData;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: Mateusz Czyrny
 * Date: 12/10/14
 * Time: 8:34 AM
 */
@Repository
public class VisitCardSearchRepositoryImpl implements VisitCardSearchRepository {

    private static final String REGEX = " ";
    private static final String LIKE = "*";
    private static final String PERSON_SURNAME = "person.surname";
    private static final String PERSON_NAME = "person.name";
    private static final String PERSON_COMPANY_NAME = "person.company.name";
    private static final String CONTACT_PERSON_SURNAME = "contactPerson.surname";
    private static final String CONTACT_PERSON_NAME = "contactPerson.name";
    private static final String CONTACT_PERSON_COMPANY_NAME = "contactPerson.company.name";
    private static final String CONTACT_PERSON_DOCUMENT_IDENTIFIER = "contactPerson.documentIdentifier";
    private static final String PERSON_DOCUMENT_IDENTIFIER = "person.documentIdentifier";


    private static final String VISIT_CARD_NUMBER = "number";




    private EntityManager entityManager;


    public Page findBySearchText(String searchText, TypeShowData typeShowData,Pageable pageable) throws Exception {
        String[] phrases = searchText.toLowerCase().split(REGEX);

        try {
            FullTextEntityManager fullTextEntityManager = Search.
                    getFullTextEntityManager(entityManager);

            QueryBuilder queryBuilder =
                    fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(VisitCard.class).get();

            Query query = buildQuery(phrases,typeShowData, queryBuilder);

            FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, VisitCard.class);
            //Sort sort = new Sort( new SortField( "number",SortField.Type.STRING, true ) );
           // List<VisitCard> result =jpaQuery.setSort(sort).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

            Sort sort = new Sort( new SortField( "createdDateToSort",SortField.Type.LONG, true ) );
            List<VisitCard> result =jpaQuery.setSort(sort).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

//            List<VisitCard> visitsCards = result.stream().sorted(comparing(VisitCard::getId).reversed()).collect(Collectors.toList());

            Page page  = new PageImpl(result,pageable,jpaQuery.getResultSize());
            return page;


        } catch (Exception e) {
            throw e;

        }
    }

    @Override
    public List<VisitCard> findBySearchText(String searchText) {
        String[] phrases = searchText.toLowerCase().split(REGEX);

        try {
            FullTextEntityManager fullTextEntityManager = Search.
                    getFullTextEntityManager(entityManager);

            QueryBuilder queryBuilder =
                    fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(VisitCard.class).get();

            Query query = buildQuery(phrases,TypeShowData.ACTIVE, queryBuilder);

            FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, VisitCard.class);
            List<VisitCard> result = jpaQuery.getResultList();
            return result;


        } catch (Exception e) {
            throw e;

        }
    }


    private Query buildQuery(String[] phrases,TypeShowData typeShowData, QueryBuilder qb) {
        BooleanJunction<BooleanJunction> query = qb.bool();
        for (String phrase : phrases) {
//            query.must(qb.keyword().onField(ACTIVE).matching(true).createQuery());
            query.must(qb.keyword().wildcard().onField(VISIT_CARD_NUMBER)
//                    .andField(DIRECTION_TYPE)
                    .andField(PERSON_SURNAME).andField(PERSON_NAME).andField(PERSON_COMPANY_NAME).andField(PERSON_DOCUMENT_IDENTIFIER).
//                    .
                    andField(CONTACT_PERSON_SURNAME).andField(CONTACT_PERSON_NAME).andField(CONTACT_PERSON_COMPANY_NAME).andField(CONTACT_PERSON_DOCUMENT_IDENTIFIER)
//                    .andField(CONTACT_PERSON_TYPE)

//                    .andField(ENTRY_DATE).andField(EXIT_DATE)
                    .matching(LIKE + phrase + LIKE).createQuery());



        }

        if(TypeShowData.ACTIVE == typeShowData){
            query.must(qb.keyword().onField("active").matching(true).createQuery());
        }else{
            if(TypeShowData.NOT_ACTIVE == typeShowData)
                query.must(qb.keyword().onField("active").matching(false).createQuery());
        }

        return query.createQuery();
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
