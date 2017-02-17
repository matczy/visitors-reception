package app.repository;


import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * User: Mateusz Czyrny
 * Date: 12/10/14
 * Time: 8:34 AM
 */
@Repository
public class IndexedEntityRepository {


    private EntityManager entityManager;

    @Transactional
    public void index() throws Exception {
        try {

            FullTextEntityManager fullTextEntityManager =
                    Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();

        } catch (Exception e) {
            throw e;
        }
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
