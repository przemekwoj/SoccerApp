package com.przemo.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.przemo.MainSoccerApp.entities.Player;

public class PlayerDao implements PlayerDaoInterface<Player, String>
{
	
private Session currentSession;
	
	private Transaction currentTransaction;

	public PlayerDao() {}
	
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
	public void persist(Player entity) 
	{
		getCurrentSession().save(entity);
	}

	@Override
	public void update(Player entity) 
	{
		getCurrentSession().update(entity);
	}

	@Override
	public Player findById(String id) 
	{
		Player player = (Player) getCurrentSession().get(Player.class, id);
		return player; 
	}

	@Override
	public void delete(Player entity)
	{
		getCurrentSession().delete(entity);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Player> findAll() 
	{
		List<Player> player = (List<Player>) getCurrentSession().createQuery("from Player").list();
		return player;
	}

	@Override
	public void deleteAll() 
	{
		List<Player> entityList = findAll();
		for (Player entity : entityList) {
			delete(entity);
		}
	}

}
