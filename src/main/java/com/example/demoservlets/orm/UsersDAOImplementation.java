package com.example.demoservlets.orm;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class UsersDAOImplementation implements UsersDAO{

    public UsersDAOImplementation() {}

    @Override
    public User get(long id) {
        return (User) HibernateSessionFactory.getSessionFactory().openSession().get(User.class, id);
    }

    @Override
    public List<User> getAll() {
        try {
            return HibernateSessionFactory.getSessionFactory().openSession().createCriteria(User.class).list();
        } catch (HibernateException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void add(User dataSet) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(dataSet);
        transaction.commit();
        session.close();
    }
}
