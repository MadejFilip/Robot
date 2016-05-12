package com.epam.ja.kmw.viewer;

import java.io.IOException;

import com.epam.ja.kmw.controller.MainLayoutController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author filipm Provides function needed to shows books on layout.
 */
public class FreeBookViewer {
	private MainLayoutController controller;
	private Stage primaryStage;

	/**
	 * Creates FreeBookViewer object and initializes it.
	 * 
	 * @param stage
	 *            to initialize object
	 */
	public FreeBookViewer(Stage stage) {
		this.primaryStage = stage;
	}

	/**
	 * Shows main layout.
	 */
	public void showMainUserLayout() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FreeBookViewer.class.getResource("/MainLayout.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();

			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			primaryStage.show();
			controller = loader.getController();
			controller.setStage(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
