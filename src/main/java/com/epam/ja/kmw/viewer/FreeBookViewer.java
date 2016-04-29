package com.epam.ja.kmw.viewer;

import java.io.IOException;

import com.epam.ja.kmw.controller.MainLayoutController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FreeBookViewer {
	private MainLayoutController controller;
	private Stage primaryStage;

	public FreeBookViewer(Stage stage) {
		this.primaryStage = stage;
	}

	public void showMainUserLayout() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FreeBookViewer.class.getResource("/MainLayout.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();

			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			primaryStage.show();
			setController(loader.getController());
			controller.setStage(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public MainLayoutController getController() {
		return controller;
	}

	public void setController(MainLayoutController controller) {
		this.controller = controller;
	}

}
