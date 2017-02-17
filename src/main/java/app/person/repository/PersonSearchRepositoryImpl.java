package app.person.repository;

import app.person.model.Enums.PersonType;
import app.person.model.entity.Person;
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
import java.util.Optional;

/**
 * User: Mateusz Czyrny
 * Date: 12/10/14
 * Time: 8:34 AM
 */
@Repository
public class PersonSearchRepositoryImpl implements PersonSearchRepository {

    private static final String REGEX = " ";
    private static final String LIKE = "*";
    private static final String SURNAME = "surname";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String COMPANY_NAME = "company.name";
    private static final String ACTIVE = "active";
    private static final String ID ="id";
    private static final String DOCUMENT_IDENTIFIER = "documentIdentifier";
    private EntityManager entityManager;


    public List<Person> findBySearchText(String searchText, Optional<PersonType> personType) throws Exception {
        String[] phrases = searchText.toLowerCase().split(REGEX);

        try {
            FullTextEntityManager fullTextEntityManager = Search.
                    getFullTextEntityManager(entityManager);

            QueryBuilder queryBuilder =
                    fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Person.class).get();

            Query query = buildQuery(phrases, queryBuilder, personType);
            FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Person.class);

            return jpaQuery.getResultList();


        } catch (Exception e) {
            throw e;

        }
    }

    public Page findBySearchText(String searchText, Optional<PersonType> personType, Pageable pageable) throws Exception {
        String[] phrases = searchText.toLowerCase().split(REGEX);

        try {
            FullTextEntityManager fullTextEntityManager = Search.
                    getFullTextEntityManager(entityManager);

            QueryBuilder queryBuilder =
                    fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Person.class).get();

            Query query = buildQuery(phrases, queryBuilder, personType);
            FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Person.class);

          Sort sort = new Sort( new SortField( "name",SortField.Type.STRING, false ) );
          // Sort sort = new Sort( new SortField( "createdDate",SortField.Type.LONG, true ) );

            List<Person> result =jpaQuery.setSort(sort).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
//            List<Person> persons = result.stream().sorted(comparing(Person::getSurname)).collect(Collectors.toList());

            Page page  = new PageImpl(result,pageable,jpaQuery.getResultSize());
            return page;

        } catch (Exception e) {
            throw e;

        }
    }

    private Query buildQuery(String[] phrases, QueryBuilder qb, Optional<PersonType> personType) {
        BooleanJunction<BooleanJunction> query = qb.bool();
        for (String phrase : phrases) {
            query.must(qb.keyword().onField(ACTIVE).matching(true).createQuery());
            query.must(qb.keyword().wildcard().onField(SURNAME).andField(NAME).andField(COMPANY_NAME).andField(DOCUMENT_IDENTIFIER)
                    .matching(LIKE + phrase + LIKE).createQuery());
            personType.ifPresent(value -> query.must(qb.keyword().onField(TYPE).matching(personType.get()).createQuery()));
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
