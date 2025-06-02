package tr.edu.duzce.mf.bm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.duzce.mf.bm.dao.CartItemDao;
import tr.edu.duzce.mf.bm.dao.FavoriteDao;
import tr.edu.duzce.mf.bm.dao.PersonDAO;
import tr.edu.duzce.mf.bm.entity.CartItem;
import tr.edu.duzce.mf.bm.entity.Favorite;
import tr.edu.duzce.mf.bm.entity.Person;

import java.util.List;

@Service
public class FavoriteService {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteService.class);

    private final FavoriteDao favoriteDao;
    private final PersonDAO personDAO;
    private final CartItemDao cartItemDAO;

    @Autowired
    public FavoriteService(FavoriteDao favoriteDao, PersonDAO personDAO, CartItemDao cartItemDAO) {
        this.favoriteDao = favoriteDao;
        this.personDAO = personDAO;
        this.cartItemDAO = cartItemDAO;
    }

    @Transactional
    public void addFavorite(Person person, String title, String author) {
        boolean exists = favoriteDao.existsByUserAndTitleAndAuthor(person, title, author);
        if (exists) {
            throw new IllegalArgumentException("This book is already in favorites");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(person);
        favorite.setTitle(title);
        favorite.setAuthor(author);

        favoriteDao.save(favorite);
    }

    public List<Favorite> findByPerson(Person person) {
        return favoriteDao.findByUser(person);
    }

    public void delete(Favorite favItems) {
        favoriteDao.delete(favItems);
    }

    public void deleteAllFavoritesForUser(Person testPerson) {
        List<Favorite> favorites = favoriteDao.findByUser(testPerson);
        for (Favorite favorite : favorites) {
            favoriteDao.delete(favorite);
        }
    }
}
