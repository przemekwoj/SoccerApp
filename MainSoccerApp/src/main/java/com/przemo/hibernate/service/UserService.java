package com.przemo.hibernate.service;

import java.util.List;

import com.przemo.MainSoccerApp.entities.User;
import com.przemo.hibernate.dao.UserDao;

public class UserService
{
	private static UserDao userDao;

	public UserService() {
		userDao = new UserDao();
	}

	public void persist(User entity) {
		userDao.openCurrentSessionwithTransaction();
		userDao.persist(entity);
		userDao.closeCurrentSessionwithTransaction();
	}

	public void update(User entity) {
		userDao.openCurrentSessionwithTransaction();
		userDao.update(entity);
		userDao.closeCurrentSessionwithTransaction();
	}

	public User findById(String id) {
		userDao.openCurrentSession();
		User user = userDao.findById(id);
		userDao.closeCurrentSession();
		return user;
	}

	public void delete(String id) {
		userDao.openCurrentSessionwithTransaction();
		User user = userDao.findById(id);
		userDao.delete(user);
		userDao.closeCurrentSessionwithTransaction();
	}

	public List<User> findAll() {
		userDao.openCurrentSession();
		List<User> books = userDao.findAll();
		userDao.closeCurrentSession();
		return books;
	}

	public void deleteAll() {
		userDao.openCurrentSessionwithTransaction();
		userDao.deleteAll();
		userDao.closeCurrentSessionwithTransaction();
	}

	public UserDao bookDao() {
		return userDao;
	}
}
