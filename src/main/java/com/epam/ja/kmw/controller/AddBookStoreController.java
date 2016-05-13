package com.epam.ja.kmw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.BookStoreDaoImpl;
import com.epam.ja.kmw.model.BookStore;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author filipm This is controller which allows user adding new bookstore by
 *         GUI.
 */
public class AddBookStoreController {
	public static final Logger LOGGER = LogManager.getLogger(AddBookStoreController.class);

	@FXML
	private TextField nameField;
	@FXML
	private TextField urlField;
	@FXML
	private TextField nameTagField;
	@FXML
	private TextField priceTagField;
	@FXML
	private TextField nextTagField;
	@FXML
	private TextField priceValueField;
	@FXML
	private TextField authorTagField;
	@FXML
	private TextField tagsTagField;
	@FXML
	private TextField typeField;

	/**
	 * Initialize bookstore window responsible for adding books.
	 */
	public void initialize() {

	}

	/**
	 * If all fields contained in window are filled calls method addBookStore
	 * which adds bookstore to database and closes bookstore robot window
	 * responsible for adding bookstore.
	 */
	@FXML
	private void handleOk() {
		BookStore checkBookStoreDuplicate = new BookStoreDaoImpl().getBookStoreByName(nameField.getText());
		if (nameField.getText().equals("") || urlField.getText().equals("") || nameTagField.getText().equals("")
				|| priceTagField.getText().equals("") || nextTagField.getText().equals("")
				|| authorTagField.getText().equals("")
				|| (tagsTagField.getText().equals("") && typeField.getText().equals(""))) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Empty fields");
			alert.setContentText("Ooops, there was an error!");

			alert.showAndWait();
		} else if (checkBookStoreDuplicate != null && checkBookStoreDuplicate.getName().equals(nameField.getText())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("BookStore is existing");
			alert.setContentText("Ooops, there was an error!");

			alert.showAndWait();
		} else {

			BookStore bookStore = new BookStore(nameField.getText(), urlField.getText(), nameTagField.getText(),
					priceTagField.getText(), nextTagField.getText(), priceValueField.getText(),
					authorTagField.getText(), tagsTagField.getText(), typeField.getText());

			new Thread(new Runnable() {

				@Override
				public void run() {
					new BookStoreDaoImpl().addBookStore(bookStore);
				}

			}).start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage thisStage = (Stage) nameField.getScene().getWindow();
			thisStage.close();
		}
	}

	/**
	 * Close the bookstore robot window responsible for adding bookstore.
	 */
	@FXML
	private void handleCancel() {
		Stage thisStage = (Stage) nameField.getScene().getWindow();
		thisStage.close();
	}
}
