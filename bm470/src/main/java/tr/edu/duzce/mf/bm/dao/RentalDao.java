package tr.edu.duzce.mf.bm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.entity.Rental;

import java.util.List;
import java.util.Optional;

@Repository
public class RentalDao {

    private static final Logger logger = LoggerFactory.getLogger(RentalDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void saveOrUpdateRental(Rental rental) {
        Session session = sessionFactory.getCurrentSession();
        if (rental.getId() == null) {
            session.save(rental);
        } else {
            session.update(rental);
        }
    }

    public Optional<Rental> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Rental rental = session.get(Rental.class, id);
        System.out.println("Rental found with ID " + id + ": " + (rental != null ? "Yes" : "No"));
        return Optional.ofNullable(rental);
    }

    public List<Rental> findAll() {
        Session session = sessionFactory.getCurrentSession();
        try {
            List<Rental> rentals = session.createQuery("FROM Rental", Rental.class).getResultList();
            System.out.println("findAll executed, found " + (rentals != null ? rentals.size() : 0) + " records.");
            for (Rental r : rentals) {
                System.out.println("Rental: ID=" + r.getId() + ", Title=" + r.getTitle() + ", TCKN=" + (r.getUser() != null ? r.getUser().getTckn() : "null"));
            }
            return rentals;
        } catch (Exception e) {
            return List.of();
        }
    }

    public void delete(Rental rental) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(rental);
    }

    public boolean existsByUserAndTitleAndReturnedFalse(Person user, String title) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COUNT(r) FROM Rental r WHERE r.user = :user AND r.title = :title AND r.returned = false";
        Long count = (Long) session.createQuery(hql)
                .setParameter("user", user)
                .setParameter("title", title)
                .uniqueResult();
        return count > 0;
    }

    public List<Rental> findByUser(Person user) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Rental r WHERE r.user = :user";
        List<Rental> rentals = session.createQuery(hql, Rental.class)
                .setParameter("user", user)
                .getResultList();
        System.out.println("findByUser for " + user.getTckn() + " found " + rentals.size() + " records.");
        return rentals;
    }

    public void update(Rental rental) {
        Session session = sessionFactory.getCurrentSession();
        session.update(rental);

    }
}