package tr.edu.duzce.mf.bm.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tr.edu.duzce.mf.bm.entity.Admin;


@Repository
public class AdminDAO {

    private static final Logger logger = LoggerFactory.getLogger(AdminDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Admin findByTcknAndPassword(String tckn, String password) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Admin WHERE tckn = :tckn AND password = :password", Admin.class)
                .setParameter("tckn", tckn)
                .setParameter("password", password)
                .uniqueResult();

    }

}