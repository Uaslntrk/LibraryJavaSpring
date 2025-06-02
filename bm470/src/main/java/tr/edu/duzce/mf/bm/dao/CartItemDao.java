package tr.edu.duzce.mf.bm.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.duzce.mf.bm.entity.CartItem;
import tr.edu.duzce.mf.bm.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public class CartItemDao {

    private static final Logger logger = LoggerFactory.getLogger(CartItemDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveOrUpdateCartItem(CartItem item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    @Transactional
    public void delete(CartItem cartItem) {
        CartItem mergedItem = em.merge(cartItem);
        em.remove(mergedItem);
    }

    public Optional<CartItem> findByPersonAndTitleAndAuthor(Person person, String title, String author) {
        TypedQuery<CartItem> query = em.createQuery(
                "SELECT c FROM CartItem c WHERE c.person = :person AND c.title = :title AND c.author = :author", CartItem.class);
        query.setParameter("person", person);
        query.setParameter("title", title);
        query.setParameter("author", author);
        List<CartItem> result = query.getResultList();
        return result.stream().findFirst();
    }

    public List<CartItem> findByPerson(Person person) {
        TypedQuery<CartItem> query = em.createQuery(
                "SELECT c FROM CartItem c WHERE c.person = :person", CartItem.class);
        query.setParameter("person", person);
        return query.getResultList();
    }

    @Transactional
    public void deleteByPerson(Person person) {
        List<CartItem> items = findByPerson(person);
        for (CartItem item : items) {
            CartItem mergedItem = em.merge(item);
            em.remove(mergedItem);
        }
    }

}
