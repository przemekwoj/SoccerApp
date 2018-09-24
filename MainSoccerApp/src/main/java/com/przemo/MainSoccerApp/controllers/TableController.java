package com.przemo.MainSoccerApp.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.przemo.MainSoccerApp.entities.TableUser;
import com.przemo.MainSoccerApp.entities.User;
import com.przemo.hibernate.service.UserService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableController implements Initializable
{
	@FXML
	private TableView table;
	
	@FXML
	private TableColumn position;
	
	@FXML
	private TableColumn manager;
	
	@FXML
	private TableColumn points;
	
	private UserService userService = new UserService();
	
	private List<User> usersList;
	
	// showing table
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		usersList = userService.findAll();
		usersList.sort(Comparator.comparing(User::getPoints).reversed());
		
		manager.setCellValueFactory(new PropertyValueFactory<>("name"));
		points.setCellValueFactory(new PropertyValueFactory<>("points"));
		position.setCellValueFactory(new PropertyValueFactory<>("position"));
		
		int i = 0;
		for(User u: usersList)
		{
			i++;
			table.getItems().add(new TableUser(i, u.getUsername(), u.getPoints()));
		}
		
	}

}
