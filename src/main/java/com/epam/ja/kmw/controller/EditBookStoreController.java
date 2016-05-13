package com.epam.ja.kmw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.BookStoreDaoImpl;

import com.epam.ja.kmw.model.BookStore;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author filipm This is controller which allows user editing existing
 *         bookstores by GUI.
 */
public class EditBookStoreController {
	public static final Logger LOGGER = LogManager.getLogger(EditBookStoreController.class);
	public static String tabPanel;
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

	private BookStore bookStoreNew;
	private BookStore bookStoreOld;

	/**
	 * Initialize bookstore window responsible for editing books. Fills all
	 * fields with proper informations.
	 */
	public void initialize() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl();
				bookStoreOld = bookStoreDaoImpl.getBookStoreByName(tabPanel);
				bookStoreOld.getId();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						nameField.setText(bookStoreOld.getName());
						urlField.setText(bookStoreOld.getUrl());
						nameTagField.setText(bookStoreOld.getNameTag());
						priceTagField.setText(bookStoreOld.getPriceTag());
						nextTagField.setText(bookStoreOld.getNextTag());
						priceValueField.setText(bookStoreOld.getPriceValue());
						authorTagField.setText(bookStoreOld.getAuthorTag());
						tagsTagField.setText(bookStoreOld.getTagsTag());
						typeField.setText(bookStoreOld.getType());
					}

				});

				LOGGER.error("Can't close database connection");

			}

		}).start();

	}

	/**
	 * If all fields contained in a window are filled calls the method
	 * updateBookStore which updates bookstore in a database and closes the
	 * bookstore robot window responsible for editing a bookstore.
	 */
	@FXML
	private void handleOk() {
		if (nameField.getText().equals("") || urlField.getText().equals("") || nameTagField.getText().equals("")
				|| priceTagField.getText().equals("") || nextTagField.getText().equals("")
				|| authorTagField.getText().equals("")) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Empty fields");
			alert.setContentText("Ooops, there was an error!");

			alert.showAndWait();
		} else {

			bookStoreNew = new BookStore(nameField.getText(), urlField.getText(), nameTagField.getText(),
					priceTagField.getText(), nextTagField.getText(), priceValueField.getText(),
					authorTagField.getText(), tagsTagField.getText(), typeField.getText());
			bookStoreNew.setId(bookStoreOld.getId());
			new Thread(new Runnable() {

				@Override
				public void run() {

					BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl();

					bookStoreDaoImpl.updateBookStore(bookStoreNew);

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
	 * Close the bookstore robot window responsible for editing a bookstore.
	 */
	@FXML
	private void handleCancel() {
		Stage thisStage = (Stage) nameField.getScene().getWindow();
		thisStage.close();
	}
}
