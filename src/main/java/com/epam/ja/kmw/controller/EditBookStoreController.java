package com.epam.ja.kmw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.BookStoreDaoImpl;
import com.epam.ja.kmw.dao.impl.ConnectionDao;
import com.epam.ja.kmw.model.BookStore;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditBookStoreController {
	public static final Logger LOGGER = LogManager.getLogger(EditBookStoreController.class);
	public static String tabPanel;
	private String bookStoreName;
	private int idBookEdit;

	/**
	 * Sets sent String as book store name.
	 * 
	 * @param bookStoreName
	 *            name of the bookstore
	 */
	public void setBookStoreName(String bookStoreName) {
		this.bookStoreName = bookStoreName;
	}

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

	private BookStore bookStore;

	/**
	 * Initialize bookstore window responsible for editing books. Fills all
	 * fields with proper informations.
	 */
	public void initialize() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				ConnectionDao connectionDao = new ConnectionDao();
				BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl(connectionDao);
				bookStore = bookStoreDaoImpl.getBookStoreByName(tabPanel);
				idBookEdit = bookStore.getId();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						nameField.setText(bookStore.getName());
						urlField.setText(bookStore.getUrl());
						nameTagField.setText(bookStore.getNameTag());
						priceTagField.setText(bookStore.getPriceTag());
						nextTagField.setText(bookStore.getNextTag());
						priceValueField.setText(bookStore.getPriceValue());
						authorTagField.setText(bookStore.getAuthorTag());
						tagsTagField.setText(bookStore.getTagsTag());
						typeField.setText(bookStore.getType());
					}

				});

				LOGGER.error("Can't close database connection");
				connectionDao.close();
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

			BookStore bookStore = new BookStore(nameField.getText(), urlField.getText(), nameTagField.getText(),
					priceTagField.getText(), nextTagField.getText(), priceValueField.getText(),
					authorTagField.getText(), tagsTagField.getText(), typeField.getText());
			bookStore.setId(idBookEdit);

			new Thread(new Runnable() {

				@Override
				public void run() {

					try (ConnectionDao connectionDao = new ConnectionDao()) {

						BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl(connectionDao);

						bookStoreDaoImpl.updateBookStore(bookStore);
					} catch (Exception e) {
						LOGGER.error("Can't close database connection");
					}
				}

			}).start();

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
