package tr.edu.duzce.mf.bm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.duzce.mf.bm.dao.CartItemDao;
import tr.edu.duzce.mf.bm.dao.PersonDAO;
import tr.edu.duzce.mf.bm.dao.RentalDao;
import tr.edu.duzce.mf.bm.entity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private static final Logger logger = LoggerFactory.getLogger(CartItemService.class);

    private final CartItemDao cartItemDAO;
    private final PersonDAO personDAO;
    private final RentalDao rentalDAO;

    @Autowired
    public CartItemService(CartItemDao cartItemDAO, PersonDAO personDAO, RentalDao rentalDAO) {
        this.cartItemDAO = cartItemDAO;
        this.personDAO = personDAO;
        this.rentalDAO = rentalDAO;
    }
    public List<CartItem> findByPerson(Person person) {
        return cartItemDAO.findByPerson(person);
    }

    @Transactional
    public String addBookToCart(Person person, String title, String author) {
        System.out.println(">>> addBookToCart çağrıldı: " + title + ", " + author);
        boolean isRented = rentalDAO.existsByUserAndTitleAndReturnedFalse(person, title);
        if (isRented) {
            return "Bu kitap zaten kiralanmış ve teslim edilmemiş, sepete eklenemez.";
        }

        Optional<CartItem> existingItem = cartItemDAO.findByPersonAndTitleAndAuthor(person, title, author);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            cartItemDAO.saveOrUpdateCartItem(item);
        } else {
            CartItem item = new CartItem();
            item.setPerson(person);
            item.setTitle(title);
            item.setAuthor(author);
            item.setQuantity(1);
            cartItemDAO.saveOrUpdateCartItem(item);
        }
        return "Kitap sepete başarıyla eklendi.";
    }

    public void delete(CartItem cartItem) {
        cartItemDAO.delete(cartItem);
    }

    @Transactional
    public void rentBooks(Person person) {
        List<CartItem> cartItems = cartItemDAO.findByPerson(person);
        for (CartItem item : cartItems) {
            Rental rental = new Rental();
            rental.setUser(person);
            rental.setTitle(item.getTitle());
            rental.setAuthor(item.getAuthor());
            rental.setRentalDate(LocalDate.now());
            rental.setReturned(false);
            rentalDAO.saveOrUpdateRental(rental);
        }

        cartItemDAO.deleteByPerson(person);
    }
}
