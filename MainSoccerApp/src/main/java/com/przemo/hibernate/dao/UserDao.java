package com.przemo.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.przemo.MainSoccerApp.entities.User;


public class UserDao implements UserDaoInterface<User, String>
{
	
private Session currentSession;
	
	private Transaction currentTransaction;

	public UserDao() {}
	
	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		currentSession.close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}
	
	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	@Override
	public void persist(User entity) 
	{
		getCurrentSession().save(entity);
	}

	@Override
	public void update(User entity) 
	{
		getCurrentSession().update(entity);
	}

	@Override
	public User findById(String id) 
	{
		User user = (User) getCurrentSession().get(User.class, id);
		return user; 
	}

	@Override
	public void delete(User entity)
	{
		getCurrentSession().delete(entity);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findAll() 
	{
		List<User> user = (List<User>) getCurrentSession().createQuery("from User").list();
		return user;
	}

	@Override
	public void deleteAll() 
	{
		List<User> entityList = findAll();
		for (User entity : entityList) {
			delete(entity);
		}
	}

}
