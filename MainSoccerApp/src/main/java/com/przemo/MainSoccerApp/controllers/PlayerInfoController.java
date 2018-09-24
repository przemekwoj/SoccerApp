package com.przemo.MainSoccerApp.controllers;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.przemo.MainSoccerApp.entities.Player;
import com.przemo.hibernate.service.PlayerService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class PlayerInfoController implements Initializable
{
	@FXML
	private Text namePlayer;
	@FXML
	private Button trainingButton;
	@FXML
	private ProgressBar powerBar;
	@FXML
	private ProgressBar staminaBar;
	@FXML
	private ImageView faceimage;
	@FXML
	private Button changePhotoButton;
	
	private PlayerService playerService = new PlayerService();
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		String urlImage = ManageController.actualPlayer.getPath();
		Image image = new Image("file:///C:\\Users\\przemoemo\\eclipse-workspace\\MainSoccerApp\\src\\main\\java\\image\\"+urlImage);
		faceimage.setImage(image);
		String info = ManageController.actualPlayer.getName() +" "+ManageController.actualPlayer.getSurname() +
		System.lineSeparator() + ManageController.actualPlayer.getPosition();
		namePlayer.setText(info);
		staminaBar.setProgress((double)ManageController.actualPlayer.getStamina() / 100);
		powerBar.setProgress((double)ManageController.actualPlayer.getPower() / 100);
		if(!ManageController.actualPlayer.isCanPlay())
		{
			trainingButton.setVisible(false);
		}
	}
	
	public void changePhoto() throws IOException
	{		
		// searching for photo
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("change photo");
		filechooser.setInitialDirectory(new File("C:\\Users\\przemoemo\\eclipse-workspace\\MainSoccerApp\\src\\main\\java\\image\\"));
		// search only jpg
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter( "JPG files (*.jpg)", "*.JPG", "*.jpg");
		filechooser.setSelectedExtensionFilter(filter);
		File file = filechooser.showOpenDialog(null);
		// change file to buffered image
		BufferedImage img = ImageIO.read(file);
		// changing new photo directory to this path
		File outputfile = new File("C:\\Users\\przemoemo\\eclipse-workspace\\MainSoccerApp\\src\\main\\java\\image\\"+file.getName());
		if(!file.exists())
		{
		file.getParentFile().mkdirs(); 
		file.createNewFile();
		}
	    ImageIO.write(img, "jpg", outputfile);
	    // changign photo of the actual player and change it in Database
	    Player playerPhoto = playerService.findById(String.valueOf(ManageController.actualPlayer.getId()));
	    playerPhoto.setPath(file.getName());
	    playerService.update(playerPhoto);
	    				//changing player in array on circle for him
	    ManageController.AllPlayers[ManageController.indexOfPlayerInArray] = playerPhoto;
	    ManageController.AllPlayers[ManageController.indexOfPlayerInArray].setCircle(ManageController.arrayCirclePlayers[ManageController.indexOfPlayerInArray].getId());
	    String urlImage = ManageController.actualPlayer.getPath();		
		Image image = new Image("file:///C:\\Users\\przemoemo\\eclipse-workspace\\MainSoccerApp\\src\\main\\java\\image\\"+file.getName());
		faceimage.setImage(image);
	}
	
	public void training()
	{
		if(ManageController.actualPlayer.getStamina() > 20)
		{
		Date dt = new Date();
	    Date newDate = new Date(dt.getTime() + TimeUnit.HOURS.toMillis(2)); // Adds 2 hours		
		ManageController.actualPlayer.setDateTrain(newDate);
		ManageController.actualPlayer.setPower(ManageController.actualPlayer.getPower() + 5);
		ManageController.actualPlayer.setStamina(ManageController.actualPlayer.getStamina() - 20);
		playerService.update(ManageController.actualPlayer);
		}
	}
	
}
