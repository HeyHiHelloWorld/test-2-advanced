package pl.kurs.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.kurs.model.CalculationEvent;

@Repository
public class CalculationEventDao implements ICalculationEventDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(CalculationEvent calculationEvent) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(calculationEvent);
        tx.commit();
        entityManager.close();
    }
}
