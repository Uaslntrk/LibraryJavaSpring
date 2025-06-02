package tr.edu.duzce.mf.bm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.duzce.mf.bm.dao.PersonDAO;
import tr.edu.duzce.mf.bm.entity.Person;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

/**
 *  Service for Person Dao
 * */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true,
        rollbackFor = Exception.class)
public class PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonDAO personDAO;

    @Transactional
    public void deleteUser(String tckn) {
        logger.info("deleteUser() çağırıldı. Kullanıcı silindi: " + tckn);
        personDAO.deletePerson(tckn);
    }

    @Transactional
    public void savePerson(Person person) {
        if(person.getPassword() != null && !person.getPassword().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(person.getPassword(), BCrypt.gensalt());
            person.setPassword(hashedPassword);
        }
        personDAO.saveOrUpdatePerson(person);
        StringBuilder builder = new StringBuilder();
        String user = builder.append(person.getFirstName()).append(" ").append(person.getLastName()).toString();
        logger.info("savePerson() çağırıldı. Kullanıcı kaydı: " + user);

    }

    @Transactional
    public List<Person> getAllPersons() {
        logger.info("getAllPersons() çağırıldı. Kullanıcılar listelendi.");
        List<Person> persons = personDAO.loadAllPersons();
        return persons;
    }

    @Transactional
    public Person getPerson(String tckn) {
        logger.info("gettPerson() çağırıldı. Kullanıcı listelendi: "+ tckn);
        return personDAO.loadPerson(tckn);
    }

    @Transactional
    public Person login(String tckn, String password) {
        Person person=personDAO.findByTckn(tckn);

        if (person != null) {
            if (BCrypt.checkpw(password, person.getPassword())) {
                return person; // Şifre
            }
        }
        return null;
    }

    @Transactional
    public void banUser(String tckn) {
        personDAO.banUserByTckn(tckn);
    }

}
