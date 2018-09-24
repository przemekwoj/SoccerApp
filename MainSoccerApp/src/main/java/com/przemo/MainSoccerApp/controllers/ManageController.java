package com.przemo.MainSoccerApp.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.przemo.MainSoccerApp.entities.Player;
import com.przemo.MainSoccerApp.entities.User;
import com.przemo.hibernate.service.PlayerService;
import com.przemo.hibernate.service.UserService;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManageController implements Initializable
{
	
	@FXML
	private Text welcomeText;
	@FXML
	private Text textPlayer1;
	@FXML
	private Text textPlayer2;
	@FXML
	private Text textPlayer3;
	@FXML
	private Text textPlayer4;
	@FXML
	private Text textPlayer5;
	@FXML
	private Text textPlayer6;
	@FXML
	private Text textPlayer7;
	@FXML
	private Text textPlayer8;
	@FXML
	private Text textPlayer9;
	@FXML
	private Text textPlayer10;
	@FXML
	private Text textPlayer11;
	@FXML
	private Text textPlayer12;
	@FXML
	private Text textPlayer13;
	@FXML
	private Text textPlayer14;
	@FXML
	private Text textPlayer15;
	
	
	@FXML
	private Circle circlePlayer1;
	@FXML
	private Circle circlePlayer2;
	@FXML
	private Circle circlePlayer3;
	@FXML
	private Circle circlePlayer4;
	@FXML
	private Circle circlePlayer5;
	@FXML
	private Circle circlePlayer6;
	@FXML
	private Circle circlePlayer7;
	@FXML
	private Circle circlePlayer8;
	@FXML
	private Circle circlePlayer9;
	@FXML
	private Circle circlePlayer10;
	@FXML
	private Circle circlePlayer11;
	@FXML
	private Circle circlePlayer12;
	@FXML
	private Circle circlePlayer13;
	@FXML
	private Circle circlePlayer14;
	@FXML
	private Circle circlePlayer15;
	
	@FXML
	private Rectangle pitchRec;
	@FXML
	private Rectangle LN;
	@FXML
	private Rectangle N;
	@FXML
	private Rectangle PN;
	@FXML
	private Rectangle PP;
	@FXML
	private Rectangle SP;
	@FXML
	private Rectangle LP;
	@FXML
	private Rectangle LO;
	@FXML
	private Rectangle SO;
	@FXML
	private Rectangle PO;
	@FXML
	private Rectangle GK;
	
	@FXML
	private Text powerTextField;
	@FXML
	private Text matchResult;
	
	@FXML
	private Button table;
	
	@FXML
	private Button playButton;
	
	static Text[] arrayTextPlayers = new Text[15];
	static Circle[] arrayCirclePlayers = new Circle[15];
	public static Player[] AllPlayers;
	static int indexOfPlayerInArray;
	// info about selected player
	static Player actualPlayer;
	
	static double power = 0;
	
	private double addpower = 0;
	private double removepower = 0;
	private double factorpower;
	private String position;
	
	
	// mause position
	private double orgSceneX, orgSceneY;
	// translate value 
    private double orgTranslateX, orgTranslateY;
    //  begin position of circle (player)
    private double StartPosX,StartPosY;
    
    private boolean clickOnPitch = false;
    private boolean releaseOnPitch = false;
    private int numberOfPlayerOnthePitch = 0; // should be 11 
	
	private PlayerService playerService = new PlayerService();
	private UserService userService = new UserService();
	
	//seconds to countdown
	private int sec;
	
	/// inner class , refreshing data from DB
	class RefreshPlayer implements Runnable
	{
		
		@Override
		public void run()
		{
			while(true)
			{
			List<Player> lp = playerService.findAll();
			int i = 0;
			// setting only acutal manager player from database
			for(Player p: lp)
			{	
				if(p.getIdmanager() == MainSceneController.idManager)
				{
				AllPlayers[i] = p;
				i++;
				}
			}
			for( i = 0 ;i<15;i++)
			{
				arrayTextPlayers[i].setText(AllPlayers[i].getPosition());
			}
			for(i = 0; i<AllPlayers.length;i++)
			{
			AllPlayers[i].setCircle(arrayCirclePlayers[i].getId());
			}
			checkIfCanPlay();
			changeCircleColor();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{	
		//set welcome text
		welcomeText.setText("WELCOME " + MainSceneController.uname);
		/// getting once all player from DB and put them to local Array
		List<Player> lp = playerService.findAll();
		
		// we have always 15 playrs
		final int sizeTable = 15;
		AllPlayers = new Player[sizeTable];
		
		int i = 0;
		// setting only acutal manager player from database
		for(Player p: lp)
		{
			if(p.getIdmanager() == MainSceneController.idManager)
			{
			AllPlayers[i] = p;
			i++;
			}
		}	
		//putting all components to array
		setToArray();
		/// set players name textfield
		for( i = 0 ;i<15;i++)
		{
			arrayTextPlayers[i].setText(AllPlayers[i].getPosition());
		}
		//setting events for circles
		setCircleEvents();
		changeColors("TRANSPARENT");
		pitchRec.toBack();
		// refreshing data from DB
		Thread watek = new Thread(new RefreshPlayer());
		watek.start();
		
		
	}
	
	// combine players with forms(shapes) in screen
	public void setToArray()
	{
		arrayTextPlayers[0] = textPlayer1;
		arrayTextPlayers[1] = textPlayer2;
		arrayTextPlayers[2] = textPlayer3;
		arrayTextPlayers[3] = textPlayer4;
		arrayTextPlayers[4] = textPlayer5;
		arrayTextPlayers[5] = textPlayer6;
		arrayTextPlayers[6] = textPlayer7;
		arrayTextPlayers[7] = textPlayer8;
		arrayTextPlayers[8] = textPlayer9;
		arrayTextPlayers[9] = textPlayer10;
		arrayTextPlayers[10] = textPlayer11;
		arrayTextPlayers[11] = textPlayer12;
		arrayTextPlayers[12] = textPlayer13;
		arrayTextPlayers[13] = textPlayer14;
		arrayTextPlayers[14] = textPlayer15;
		
		arrayCirclePlayers[0] = circlePlayer1;
		arrayCirclePlayers[1] = circlePlayer2;
		arrayCirclePlayers[2] = circlePlayer3;
		arrayCirclePlayers[3] = circlePlayer4;
		arrayCirclePlayers[4] = circlePlayer5;
		arrayCirclePlayers[5] = circlePlayer6;
		arrayCirclePlayers[6] = circlePlayer7;
		arrayCirclePlayers[7] = circlePlayer8;
		arrayCirclePlayers[8] = circlePlayer9;
		arrayCirclePlayers[9] = circlePlayer10;
		arrayCirclePlayers[10] = circlePlayer11;
		arrayCirclePlayers[11] = circlePlayer12;
		arrayCirclePlayers[12] = circlePlayer13;
		arrayCirclePlayers[13] = circlePlayer14;
		arrayCirclePlayers[14] = circlePlayer15;
		
		for(int i = 0; i<AllPlayers.length;i++)
		{
		AllPlayers[i].setCircle(arrayCirclePlayers[i].getId());
		}
		
	}
	
	public void setCircleEvents()
	{
		for(int i = 0;i<arrayCirclePlayers.length ;i++)
		{
			arrayCirclePlayers[i].setOnMousePressed(circleOnMousePressedEventHandler);
			arrayCirclePlayers[i].setOnMouseDragged(circleOnMouseDraggedEventHandler);
			arrayCirclePlayers[i].setOnMouseReleased(circleOnMouseReleasedEventHandler);
		}
	}
	
	 public void setTextPosition()
	  {
		  for(int i =0;i<arrayCirclePlayers.length ;i++)
		  {
			  arrayTextPlayers[i].setTranslateX(arrayCirclePlayers[i].getTranslateX());
			  arrayTextPlayers[i].setTranslateY(arrayCirclePlayers[i].getTranslateY());
			  arrayTextPlayers[i].toFront();
			  arrayCirclePlayers[i].toFront();
			  
		  }
	  }
	 
	 public String checkPositionPlayer(Circle circle)
	 {
		 String position="";
		for(int i = 0;i<arrayCirclePlayers.length ;i++)
		{
			if(arrayCirclePlayers[i].equals(circle))
				{
					position = arrayTextPlayers[i].getText();
				}
		}
		 return position;
	 }
	 
	
	EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
	        new EventHandler<MouseEvent>() {
			
	        @Override
	        public void handle(MouseEvent t) {	        			
	        	// mouse position on root screen
	            orgSceneX = t.getSceneX();
	            orgSceneY = t.getSceneY();
	            //start position of circle
	            StartPosX = ((Circle)(t.getSource())).getLayoutX();
	            StartPosY = ((Circle)(t.getSource())).getLayoutY();
	            //diffrence between start position and added position , here is equal to 0
	            orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
	            orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
	            position = checkPositionPlayer(((Circle)(t.getSource())));
	            changeColors(position);
	            clickOnPitch = checkIfClickIsOnThePitch(orgSceneX, orgSceneY);
	            caltulateRemovePower(orgSceneX,orgSceneY);
	        }
	    };
	     
	    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = 
	        new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent t) {
	        	boolean canPlay = false;
		    	for(Player p: AllPlayers)
		    		{
		       		if(((Circle)(t.getSource())).getId().equals(p.getCircle()))
		       			{
		       				canPlay = p.isCanPlay();
		       				break;
		        		}
		    		}
		    	if(canPlay)
		    	{
	            double offsetX = t.getSceneX() - orgSceneX;
	            double offsetY = t.getSceneY() - orgSceneY;
	            double newTranslateX = orgTranslateX + offsetX;
	            double newTranslateY = orgTranslateY + offsetY;
	             // set new position of circle
	            ((Circle)(t.getSource())).setTranslateX(newTranslateX);
	            ((Circle)(t.getSource())).setTranslateY(newTranslateY);
	            setTextPosition();
		    	}
	        }
	    };
	    
	    EventHandler<MouseEvent> circleOnMouseReleasedEventHandler = 
		        new EventHandler<MouseEvent>() {
		 
		        @Override
		        public void handle(MouseEvent t) {
	
		        	//we are checking here if player is "on a pitch" on rectagle 
		        	// if not , player ( circle) is getting back to the bench(start position)
		        	double actualX = t.getSceneX();
		            double actualY = t.getSceneY();	
		            releaseOnPitch = checkIfClickIsOnThePitch(actualX, actualY);
		            addPlayerOnPitch(clickOnPitch,releaseOnPitch);
		            	if( (actualX < pitchRec.getLayoutX())||(actualX > (pitchRec.getLayoutX()+pitchRec.getWidth()) ))
		            	{
		            		((Circle)(t.getSource())).setTranslateX(0);
		    	            ((Circle)(t.getSource())).setTranslateY(0);
		            	}
		            	if((actualY < pitchRec.getLayoutY()) ||(actualY > ( pitchRec.getLayoutY() + pitchRec.getHeight()) ))
	            		{
		            		((Circle)(t.getSource())).setTranslateX(0);
		    	            ((Circle)(t.getSource())).setTranslateY(0);
	            		}
			            System.out.println(numberOfPlayerOnthePitch);
		            	calculateAddPower(actualX,actualY);
		            	calculatePower(clickOnPitch,releaseOnPitch);
		            	if(numberOfPlayerOnthePitch > 11)
		            	{
		            		((Circle)(t.getSource())).setTranslateX(0);
		    	            ((Circle)(t.getSource())).setTranslateY(0);
		    	            // just for mathematics
		    	            numberOfPlayerOnthePitch--;
		            	}		            	
		            	setTextPosition();
		            	changeColors("TRANSPARENT");
		            	
		            	//openinformation player window
		            	if( (orgSceneX - t.getSceneX() == 0 )&&( orgSceneY -t.getSceneY() == 0))
		            	{
		            		int i = 0;
		            		for(Player p: AllPlayers)
		            		{
		            			if(((Circle)(t.getSource())).getId().equals(p.getCircle()))
		            			{	
		            				indexOfPlayerInArray = i;
		            				actualPlayer = p;
		            				URL url;
		            				 Parent root;
									try {
										url = new File("src/main/java/com/przemo/MainSoccerApp/layout/PlayerInfo.fxml").toURL();
										root = FXMLLoader.load(url);
										String s = "com/przemo/MainSoccerApp/layout/manage.css";
										Scene scene = new Scene(root);
										scene.getStylesheets().add(s);
										Stage stage = new Stage();
				            		    stage.setTitle("INFO PLAYER");
				            			stage.setScene(scene);
				            		    // focus only on one stage(window)
				            			stage.initModality(Modality.APPLICATION_MODAL);
				            		 stage.show();
									} catch (IOException e) {
										e.printStackTrace();
									}
		            				break;
		            			}
		            		i++;
		            		}
		            		
		            	}
		        }
		        
		    };
		    
		    public void changeColors(String position)
			 {
				if(position.equals("LN"))
				{
					LN.setFill(Color.rgb(0,255,0,0.7));//green
					N.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					PN.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					PP.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					SP.setFill(Color.rgb(255,155,0,0.7));//yellow
					LP.setFill(Color.rgb(255,155,0,0.7));//yellow
					LO.setFill(Color.rgb(255,0,0,0.7));//red
					SO.setFill(Color.rgb(255,0,0,0.7));//red
					PO.setFill(Color.rgb(255,0,0,0.7));//red
					GK.setFill(Color.rgb(255,0,0,0.7));//red
				}
				else if(position.equals("N"))
				{
					LN.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					N.setFill(Color.rgb(0,255,0,0.7));//green
					PN.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					PP.setFill(Color.rgb(255,155,0,0.7));//yellow
					SP.setFill(Color.rgb(255,155,0,0.7));//yellow
					LP.setFill(Color.rgb(255,155,0,0.7));//yellow
					LO.setFill(Color.rgb(255,0,0,0.7));//red
					SO.setFill(Color.rgb(255,0,0,0.7));//red
					PO.setFill(Color.rgb(255,0,0,0.7));//red
					GK.setFill(Color.rgb(255,0,0,0.7));//red
				}
				else if(position.equals("PN"))
				{
					LN.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					N.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					PN.setFill(Color.rgb(0,255,0,0.7));//green
					PP.setFill(Color.rgb(255,155,0,0.7));//yellow
					SP.setFill(Color.rgb(255,155,0,0.7));//yellow
					LP.setFill(Color.rgb(255,155,0,0.7));//yellow
					LO.setFill(Color.rgb(255,0,0,0.7));//red
					SO.setFill(Color.rgb(255,0,0,0.7));//red
					PO.setFill(Color.rgb(255,0,0,0.7));//red
					GK.setFill(Color.rgb(255,0,0,0.7));//red
				}
				else if(position.equals("PP"))
				{
					LN.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					N.setFill(Color.rgb(255,155,0,0.7));//yellow
					PN.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					PP.setFill(Color.rgb(0,255,0,0.7));//green
					SP.setFill(Color.rgb(255,155,0,0.7));//yellow
					LP.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					LO.setFill(Color.rgb(255,0,0,0.7));//red
					SO.setFill(Color.rgb(255,0,0,0.7));//red
					PO.setFill(Color.rgb(255,0,0,0.7));//red
					GK.setFill(Color.rgb(255,0,0,0.7));//red
				}
				else if(position.equals("SP"))
				{
					LN.setFill(Color.rgb(255,155,0,0.7));//yellow
					N.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					PN.setFill(Color.rgb(255,155,0,0.7));//yellow
					PP.setFill(Color.rgb(255,0,0,0.7));//red
					SP.setFill(Color.rgb(0,255,0,0.7));//green
					LP.setFill(Color.rgb(255,0,0,0.7));//red
					LO.setFill(Color.rgb(255,0,0,0.7));//red
					SO.setFill(Color.rgb(255,155,0,0.7));//yellow
					PO.setFill(Color.rgb(255,0,0,0.7));//red
					GK.setFill(Color.rgb(255,0,0,0.7));//red
				}
				else if(position.equals("LP"))
				{
					LN.setFill(Color.rgb(255,155,0,0.7));//yellow
					N.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					PN.setFill(Color.rgb(255,155,0,0.7));//yellow
					PP.setFill(Color.rgb(255,155,0,0.7));//yellow
					SP.setFill(Color.rgb(0,255,0,0.7));//green
					LP.setFill(Color.rgb(255,0,0,0.7));//red
					LO.setFill(Color.rgb(255,0,0,0.7));//red
					SO.setFill(Color.rgb(255,0,0,0.7));//red
					PO.setFill(Color.rgb(255,0,0,0.7));//red
					GK.setFill(Color.rgb(255,0,0,0.7));//red
				}
				else if(position.equals("LO"))
				{
					LN.setFill(Color.rgb(255,0,0,0.7));//red
					N.setFill(Color.rgb(255,0,0,0.7));//red
					PN.setFill(Color.rgb(255,0,0,0.7));//red
					PP.setFill(Color.rgb(255,155,0,0.7));//yellow
					SP.setFill(Color.rgb(255,155,0,0.7));//yellow
					LP.setFill(Color.rgb(255,155,0,0.7));//yellow
					LO.setFill(Color.rgb(0,255,0,0.7));//green
					SO.setFill(Color.rgb(255,155,0,0.7));//yellow
					PO.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					GK.setFill(Color.rgb(255,0,0,0.7));//red
				}
				else if(position.equals("SO"))
				{
					LN.setFill(Color.rgb(255,0,0,0.7));//red
					N.setFill(Color.rgb(255,0,0,0.7));//red
					PN.setFill(Color.rgb(255,0,0,0.7));//red
					PP.setFill(Color.rgb(255,0,0,0.7));//red
					SP.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					LP.setFill(Color.rgb(255,0,0,0.7));//red
					LO.setFill(Color.rgb(255,155,0,0.7));//yellow
					SO.setFill(Color.rgb(0,255,0,0.7));//green
					PO.setFill(Color.rgb(255,155,0,0.7));//yellow
					GK.setFill(Color.rgb(255,0,0,0.7));//red
				}
				else if(position.equals("PO"))
				{
					LN.setFill(Color.rgb(255,0,0,0.7));//red
					N.setFill(Color.rgb(255,0,0,0.7));//red
					PN.setFill(Color.rgb(255,0,0,0.7));//red
					PP.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					SP.setFill(Color.rgb(255,155,0,0.7));//yellow
					LP.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					LO.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					SO.setFill(Color.rgb(0,59,0,0.7));//darkgreen
					PO.setFill(Color.rgb(0,255,0,0.7));//green
					GK.setFill(Color.rgb(255,0,0,0.7));//red
				}
				else if(position.equals("GK"))
				{
					LN.setFill(Color.rgb(255,0,0,0.7));//red
					N.setFill(Color.rgb(255,0,0,0.7));//red
					PN.setFill(Color.rgb(255,0,0,0.7));//red
					PP.setFill(Color.rgb(255,0,0,0.7));//red
					SP.setFill(Color.rgb(255,0,0,0.7));//red
					LP.setFill(Color.rgb(255,0,0,0.7));//red
					LO.setFill(Color.rgb(255,0,0,0.7));//red
					SO.setFill(Color.rgb(255,0,0,0.7));//red
					PO.setFill(Color.rgb(255,0,0,0.7));//red
					GK.setFill(Color.rgb(0,255,0,0.7));//green
				}
				else if(position.equals("TRANSPARENT"))
				{
					LN.setFill(Color.TRANSPARENT);
					N.setFill(Color.TRANSPARENT);
					PN.setFill(Color.TRANSPARENT);
					PP.setFill(Color.TRANSPARENT);
					SP.setFill(Color.TRANSPARENT);
					LP.setFill(Color.TRANSPARENT);
					LO.setFill(Color.TRANSPARENT);
					SO.setFill(Color.TRANSPARENT);
					PO.setFill(Color.TRANSPARENT);
					GK.setFill(Color.TRANSPARENT);
				}
			 }
		   
		    public boolean checkIfClickIsOnThePitch(double x, double y)
		    {
		    	if(x > pitchRec.getLayoutX() &&  x < pitchRec.getLayoutX() + pitchRec.getWidth() && y > pitchRec.getLayoutY() && y < pitchRec.getLayoutY() + pitchRec.getHeight())
		    	{
		    		return true;
		    	}
		    	else return false;
		    }
		    
		    public void addPlayerOnPitch(boolean clickpitch, boolean releasepitch)
		    {
		    	if(clickpitch == false && releasepitch == true)
		    	{
		    		numberOfPlayerOnthePitch++;
		    	}
		    	if(clickpitch == true && releasepitch == false)
		    	{
		    		numberOfPlayerOnthePitch--;
		    	}
		    }
		    
		    public void changeCircleColor()
		    {
		    	for(int i = 0;i<15;i++)
		    	{
		    		if(AllPlayers[i].isCanPlay() == true)
		    		{
		    			arrayCirclePlayers[i].setFill(Color.BLUE);
		    		}
		    		else
		    		{
		    			arrayCirclePlayers[i].setFill(Color.RED);	
		    		}
		    	}
		    }
		    public void checkIfCanPlay()
		    {
		    	Date actualDate = new Date();
		    	Date playerTrainDate;
		    	for(Player p: AllPlayers)
		    	{
		    		playerTrainDate = p.getDateTrain();
		    		if(playerTrainDate.compareTo(actualDate)>0)
		    		{
		    		p.setCanPlay(false);	
		    		}
		    		else
		    		{
		    		p.setCanPlay(true);	
		    		}
		    	}
		    }
		    
		    public void caltulateRemovePower(double x,double y)
		    {
		    	if(x > pitchRec.getLayoutX() && x < pitchRec.getLayoutX() + pitchRec.getWidth() && y > pitchRec.getLayoutY() && y < pitchRec.getLayoutY() + pitchRec.getHeight() )
		    	{
		    		setfactor(x,y);
		    		removepower = 10*factorpower;
		    	}
		    	else
		    	{
		    		removepower = 0;
		    	}
		    }
		    
		    public void calculateAddPower(double x,double y)
		    {
		    	if(x > pitchRec.getLayoutX() && x < pitchRec.getLayoutX() + pitchRec.getWidth() && y > pitchRec.getLayoutY() && y < pitchRec.getLayoutY() + pitchRec.getHeight() )
		    	{
		    		setfactor(x,y);
		    		addpower = 10*factorpower;
		    	}
		    	else
		    	{
		    		addpower = 0;
		    	}
		    }
		    
		    public void calculatePower(boolean clickOnPitch,boolean releaseOnPitch)
		    {
		    	if( !(numberOfPlayerOnthePitch == 12 && clickOnPitch == false && releaseOnPitch == true ))
		    	{
		    	power = power + addpower - removepower;
		    	powerTextField.setText(Double.toString(power));
		    	}
		    }
		    
		    public void setfactor(double x,double y)
		    {
		    	Paint color = Color.TRANSPARENT;
		    	// check color of position
		    	if(x > LN.getLayoutX() && x < LN.getLayoutX() + LN.getWidth() && y > LN.getLayoutY() && y < LN.getLayoutY() + LN.getHeight() )
		    	{
		    		color = LN.getFill();
		    	}
		    	if(x > N.getLayoutX() && x < N.getLayoutX() + N.getWidth() && y > N.getLayoutY() && y < N.getLayoutY() + N.getHeight() )
		    	{
		    		color = N.getFill();
		    	}
		    	if(x > PN.getLayoutX() && x < PN.getLayoutX() + PN.getWidth() && y > PN.getLayoutY() && y < PN.getLayoutY() + PN.getHeight() )
		    	{
		    		color = PN.getFill();
		    	}
		    	if(x > LP.getLayoutX() && x < LP.getLayoutX() + LP.getWidth() && y > LP.getLayoutY() && y < LP.getLayoutY() + LP.getHeight() )
		    	{
		    		color = LP.getFill();
		    	}
		    	if(x > SP.getLayoutX() && x < SP.getLayoutX() + SP.getWidth() && y > SP.getLayoutY() && y < SP.getLayoutY() + SP.getHeight() )
		    	{
		    		color = SP.getFill();
		    	}
		    	if(x > PP.getLayoutX() && x < PP.getLayoutX() + PP.getWidth() && y > PP.getLayoutY() && y < PP.getLayoutY() + PP.getHeight() )
		    	{
		    		color = PP.getFill();
		    	}
		    	if(x > LO.getLayoutX() && x < LO.getLayoutX() + LO.getWidth() && y > LO.getLayoutY() && y < LO.getLayoutY() + LO.getHeight() )
		    	{
		    		color = LO.getFill();
		    	}
		    	if(x > SO.getLayoutX() && x < SO.getLayoutX() + SO.getWidth() && y > SO.getLayoutY() && y < SO.getLayoutY() + SO.getHeight() )
		    	{
		    		color = SO.getFill();
		    	}
		    	if(x > PO.getLayoutX() && x < PO.getLayoutX() + PO.getWidth() && y > PO.getLayoutY() && y < PO.getLayoutY() + PO.getHeight() )
		    	{
		    		color = PO.getFill();
		    	}
		    	if(x > GK.getLayoutX() && x < GK.getLayoutX() + GK.getWidth() && y > GK.getLayoutY() && y < GK.getLayoutY() + GK.getHeight() )
		    	{
		    		color = GK.getFill();
		    	}
		    	// set power factor , depends on color
		    	if(color.equals(Color.rgb(0,255,0,0.7)))//green
		    	{
		    		factorpower = 4;
		    	}
		    	if(color.equals(Color.rgb(255,155,0,0.7)))//yellow
    			{
		    		factorpower = 0.6;
    			}
		    	if(color.equals(Color.rgb(0,59,0,0.7)))//dark geen
    			{
		    		factorpower = 2;
    			}
		    	if(color.equals(Color.rgb(255,0,0,0.7)))//red
    			{
		    		factorpower = 0.1;
    			}
		    	
		    }
		    
		    public void showTable()
		    {
		    	System.out.println("table showing");
		    	URL url;
				Parent root;
				try {
					url = new File("src/main/java/com/przemo/MainSoccerApp/layout/TableInfo.fxml").toURL();
					root = FXMLLoader.load(url);
					String s = "com/przemo/MainSoccerApp/layout/tablelayout.css";
					Scene scene = new Scene(root);
					scene.getStylesheets().add(s);
					Stage stage = new Stage();
					stage.setTitle("Table");
					stage.setScene(scene);
					// focus only on one stage(window)
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		   
		 
		 public void play()
		 {
			 if(numberOfPlayerOnthePitch == 11)
			 {
				startMatch();
				Thread countdown = new Thread(new Clock());
				countdown.start();
				playButton.setDisable(true);
			 }
			 else
			 {
				matchResult.setText("you need 11 players"); 
			 }
		 }
		 // its count down our time
		 class Clock implements Runnable
		 {

			@Override
			public void run() 
			{
				for(sec = 60; sec>0; sec--)
				{
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					   		playButton.setText(Integer.toString(sec));
					    }
					});
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				playButton.setDisable(false);
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	playButton.setText("PLAY");
				    }
				});
				
			}
			 
		 }
		 
		 public void startMatch()
		 {
			 double opponentPower = 0;
			 List<User> userslist = userService.findAll();
			 User actualUser = null;
			 User opponent;
			 // removing our manager from opponents
			 int num = 0;
			 for(User u: userslist)
			 {
				 if(u.getId()== MainSceneController.idManager)
				 {
					 actualUser = u;
					 System.out.println(userslist.size());
					 System.out.println("username "+actualUser.getUsername());
					 userslist.remove(num);
					 System.out.println(userslist.size());
					 break;
				 }
				 num++;
			 }
			 //choosing opponent
			 int numberOfManagers = userslist.size();
			 System.out.println("numberOfManagers "+numberOfManagers);
			 Random rand = new Random();
			 
			 int manager = rand.nextInt(numberOfManagers);
			 System.out.println("random "+manager);
			 opponent = userslist.get(manager);
			 
			 //getting opponets players
			 List<Player> lp = playerService.findAll();
			
			 
			 for(Player p: lp)
			 {
				 if(p.getIdmanager() == opponent.getId())
				 {
					 opponentPower = opponentPower + p.getPower();
				 }
			 }
			 System.out.println("my power "+power + "oppone pow "+opponentPower);
			 if(opponentPower > power)
			 {
				 matchResult.setText("you loose");
			 }
			 else if(opponentPower < power)
			 {
				 matchResult.setText("you win");
				 actualUser.setPoints(actualUser.getPoints() + 3);
			 }
			 else if(opponentPower == power)
			 {
				 matchResult.setText("you draw");
				 actualUser.setPoints(actualUser.getPoints() + 1);
			 }
			 
			 userService.update(actualUser);
		 }
		  
}
