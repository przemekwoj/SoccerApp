package com.przemo.MainSoccerApp.controllers;



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.przemo.MainSoccerApp.entities.Player;
import com.przemo.MainSoccerApp.entities.User;
import com.przemo.hibernate.service.PlayerService;
import com.przemo.hibernate.service.UserService;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainSceneController implements Initializable
{
	@FXML
	private AnchorPane achnorpane;
	@FXML
	private Button create_account;
	@FXML
	private Button login;
	@FXML
	private Button back;
	@FXML
	private Button ok;
	@FXML
	private TextField textfield_username;
	@FXML
	private TextField textfield_password;
	@FXML
	private ImageView jose;
	@FXML
	private ImageView pitchview;
	@FXML
	private Text hint;
	
	private UserService userService = new UserService();
	private PlayerService playerService = new PlayerService();
	
	// we need them to see wchich user is already online
	static String uname;
	static String pass;
	static public int idManager;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		ok.setVisible(false);
		back.setVisible(false);
		jose.setVisible(false);
	}
	
	public void login() throws IOException
	{
		 boolean loginOK = false;
		 uname = textfield_username.getText();
		 pass = textfield_password.getText();
		 List<User> listUser = userService.findAll();
			for(User u: listUser)
			{
				if(u.getUsername().equals(uname))
				{
					if(u.getPassword().equals(pass))
					{
					idManager = u.getId();
					loginOK = true;
					break;
					}
				}
			}
		if(loginOK)
		{
			//openpage
			//open new window
			Stage stage = (Stage) login.getScene().getWindow();
			URL url = new File("src/main/java/com/przemo/MainSoccerApp/layout/Manage.fxml").toURL();
			Parent root = FXMLLoader.load(url);
			Scene scene = new Scene(root);
			String s = "com/przemo/MainSoccerApp/layout/manage.css";
			scene.getStylesheets().add(s);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		}
		else
		{
			textfield_password.setText("");
			textfield_username.setText("");
			hint.setText("Wrong password or username");
		}
		 
	}
	// login in "create account " option
	public void ok() throws IOException
	{
		boolean createNewAcc = true;
		uname = textfield_username.getText();
		pass = textfield_password.getText();
		if(!uname.equals("") || !pass.equals(""))
		{
			hint.setText("");
			List<User> listUser = userService.findAll();
			for(User u: listUser)
			{
				idManager = u.getId() + 1;
				if(u.getUsername().equals(uname))
				{
					createNewAcc = false;
					break;
				}
			}
			if(createNewAcc)
			{
				//create new account
				userService.persist(new User(uname,pass,0));
				//create new standart players for user
				List<Player> listPlayer= playerService.findAll();
				for(Player p: listPlayer)
				{
					if(p.getIdmanager() == 0)
					{
						p.setIdmanager(idManager);
						playerService.persist(p);
					}
					else break;
				}
				//open new window
				Stage stage = (Stage) ok.getScene().getWindow();
				URL url = new File("src/main/java/com/przemo/MainSoccerApp/layout/Manage.fxml").toURL();
				Parent root = FXMLLoader.load(url);
				Scene scene = new Scene(root);
				String s = "com/przemo/MainSoccerApp/layout/manage.css";
				scene.getStylesheets().add(s);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			}
		}
		else
		{
			hint.setText("this is already exit , try another username");
		}
	}
	public void createAcc()
	{
		hint.setText("");
		jose.setVisible(true);
		jose.toBack();
		textfield_username.setText("");
		textfield_password.setText("");
		create_account.setVisible(false);
		login.setVisible(false);
		ok.setVisible(true);
		back.setVisible(true);
		pitchview.setVisible(false);
	}
	
	public void back()
	{
		jose.setVisible(false);
		pitchview.setVisible(true);
		pitchview.toBack();
		textfield_username.setText("");
		textfield_password.setText("");
		create_account.setVisible(true);
		login.setVisible(true);
		ok.setVisible(false);
		back.setVisible(false);
		hint.setText("");
	}
	

}
