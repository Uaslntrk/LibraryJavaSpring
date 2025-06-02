package tr.edu.duzce.mf.bm.service;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.duzce.mf.bm.controller.HomeController;
import tr.edu.duzce.mf.bm.dao.AdminDAO;
import tr.edu.duzce.mf.bm.entity.Admin;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    private final AdminDAO adminDAO;

    @Autowired
    public AdminService(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Transactional
    public Admin login(String tckn, String password) {
        return adminDAO.findByTcknAndPassword(tckn, password);
    }




}