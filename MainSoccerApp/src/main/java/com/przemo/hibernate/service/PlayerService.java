package com.przemo.hibernate.service;

import java.util.List;

import com.przemo.MainSoccerApp.entities.Player;
import com.przemo.MainSoccerApp.entities.User;
import com.przemo.hibernate.dao.PlayerDao;
import com.przemo.hibernate.dao.UserDao;

public class PlayerService
{
	private static PlayerDao playerDao;

	public PlayerService() {
		playerDao = new PlayerDao();
	}

	public void persist(Player entity) {
		playerDao.openCurrentSessionwithTransaction();
		playerDao.persist(entity);
		playerDao.closeCurrentSessionwithTransaction();
	}

	public void update(Player entity) {
		playerDao.openCurrentSessionwithTransaction();
		playerDao.update(entity);
		playerDao.closeCurrentSessionwithTransaction();
	}

	public Player findById(String id) {
		playerDao.openCurrentSession();
		Player player = playerDao.findById(id);
		playerDao.closeCurrentSession();
		return player;
	}

	public void delete(String id) {
		playerDao.openCurrentSessionwithTransaction();
		Player player = playerDao.findById(id);
		playerDao.delete(player);
		playerDao.closeCurrentSessionwithTransaction();
	}

	public List<Player> findAll() {
		playerDao.openCurrentSession();
		List<Player> books = playerDao.findAll();
		playerDao.closeCurrentSession();
		return books;
	}

	public void deleteAll() {
		playerDao.openCurrentSessionwithTransaction();
		playerDao.deleteAll();
		playerDao.closeCurrentSessionwithTransaction();
	}

	public PlayerDao bookDao() {
		return playerDao;
	}
}
