package tr.edu.duzce.mf.bm.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.duzce.mf.bm.entity.Favorite;
import tr.edu.duzce.mf.bm.entity.Person;
import java.util.List;

@Repository
public class FavoriteDao {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteDao.class);

    @PersistenceContext
    private EntityManager em;

    public List<Favorite> findByUser(Person user) {

        TypedQuery<Favorite> query = em.createQuery(
                "SELECT f FROM Favorite f WHERE f.user.tckn = :tckn", Favorite.class);
        query.setParameter("tckn", user.getTckn());
        return query.getResultList();
    }
    public boolean existsByUserAndTitleAndAuthor(Person user, String title, String author) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(f) FROM Favorite f WHERE f.user = :user AND f.title = :title AND f.author = :author", Long.class);
        query.setParameter("user", user);
        query.setParameter("title", title);
        query.setParameter("author", author);
        Long count = query.getSingleResult();
        return count > 0;
    }

    public void save(Favorite fav) {
        if (fav.getId() == null) {
            em.persist(fav);
        } else {
            em.merge(fav);
        }
    }
    @Transactional
    public void delete(Favorite favItems) {
        Favorite mergedItem = em.merge(favItems);
        em.remove(mergedItem);
    }
}
