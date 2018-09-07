package com.przemo.MainSoccerApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main  extends Application 
{

	public static void main(String[] args)
	{
		  launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		Parent root = FXMLLoader.load(getClass().getResource("layout/Main.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("layout/mainscene.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}
