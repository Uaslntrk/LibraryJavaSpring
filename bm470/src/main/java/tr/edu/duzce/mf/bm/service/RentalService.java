package tr.edu.duzce.mf.bm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.duzce.mf.bm.controller.AccountController;
import tr.edu.duzce.mf.bm.dao.CartItemDao;
import tr.edu.duzce.mf.bm.dao.PersonDAO;
import tr.edu.duzce.mf.bm.dao.RentalDao;
import tr.edu.duzce.mf.bm.entity.CartItem;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.entity.Rental;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private static final Logger logger = LoggerFactory.getLogger(RentalService.class);

    private final RentalDao rentalDAO;
    private final PersonDAO personDAO;
    private final CartItemDao cartItemDAO;

    @Autowired
    public RentalService(RentalDao rentalDAO, PersonDAO personDAO, CartItemDao cartItemDAO) {
        this.rentalDAO = rentalDAO;
        this.personDAO = personDAO;
        this.cartItemDAO = cartItemDAO;
    }

    @Transactional(readOnly = true)
    public List<Rental> findByPerson(Person person) {
        return rentalDAO.findByUser(person);
    }

    @Transactional(readOnly = true)
    public List<Rental> getAllRentals() {
        return rentalDAO.findAll();
    }

    @Transactional
    public void markAsReturned(Long rentalId) {
        Optional<Rental> rentalOpt = rentalDAO.findById(rentalId);
        if (rentalOpt.isPresent()) {
            Rental rental = rentalOpt.get();
            rental.setReturned(true);
            rental.setReturnDate(LocalDate.now());
            rentalDAO.update(rental);
        }
    }

    @Transactional
    public void deleteRental(Long rentalId) {
        Optional<Rental> rentalOpt = rentalDAO.findById(rentalId);
        if (rentalOpt.isPresent()) {
            rentalDAO.delete(rentalOpt.get());
        } else {
            throw new IllegalArgumentException("Kiralama kaydı bulunamadı: " + rentalId);
        }
    }

}
