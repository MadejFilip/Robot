package com.epam.ja.kmw.viewer;

import java.io.IOException;

import com.epam.ja.kmw.controller.MainLayoutController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FreeBookViewer extends Application {
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		showMainUserLayout();

	}

	private void showMainUserLayout() {

		AnchorPane anchorPane = new AnchorPane();

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FreeBookViewer.class.getResource("/MainLayout.fxml"));
			anchorPane = (AnchorPane) loader.load();

			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);

			@SuppressWarnings("unused")
			MainLayoutController controller = loader.getController();
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
    public void open(String...args) {
        launch(args);
    }
    
    

}
