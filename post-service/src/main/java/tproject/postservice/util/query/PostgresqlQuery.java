package tproject.postservice.util.query;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostgresqlQuery {

    @PersistenceContext
    private EntityManager entityManager;

    public <T> T findById(Class<T> entityClass, Long id) {
        return entityManager.find(entityClass, id);
    }


}
