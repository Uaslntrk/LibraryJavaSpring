package tr.edu.duzce.mf.bm.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.duzce.mf.bm.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonDAO {

    private static final Logger logger = LoggerFactory.getLogger(PersonDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager em;

    public Person findByTckn(String tckn) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Person WHERE tckn = :tckn";
        return session.createQuery(hql, Person.class)
                .setParameter("tckn", tckn)
                .uniqueResult();
    }

    public Optional<Person> findById(Long tckn) {

        return Optional.ofNullable(em.find(Person.class, tckn));
    }

    public void saveOrUpdatePerson(Person person) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(person);
    }

    public Person loadPerson(String tckn) {

        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, tckn);
    }

    public void deletePerson(String tckn) {
        if (tckn == null || tckn.trim().isEmpty()) {
            throw new IllegalArgumentException("TCKN boş olamaz");
        }
        Session session = sessionFactory.getCurrentSession();
        Person person = findByTckn(tckn);
        if (person != null) {
            session.delete(person);
            session.flush();

        } else {
            throw new RuntimeException("Kullanıcı bulunamadı: tckn=" + tckn);
        }
    }

    public void save(Person person) {
        if (person.getTckn() == null) {
            em.persist(person);
        } else {
            em.merge(person);
        }
    }

    public List<Person> loadAllPersons() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Person p", Person.class).list();
    }

    @Transactional
    public void banUserByTckn(String tckn) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.createQuery("FROM Person WHERE tckn = :tckn", Person.class)
                .setParameter("tckn", tckn)
                .uniqueResult();
        if (person != null) {
            person.setBanned(true);
            session.update(person);
        }
    }

}