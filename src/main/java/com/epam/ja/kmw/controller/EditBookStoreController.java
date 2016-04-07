package com.epam.ja.kmw.controller;

import java.sql.SQLException;

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
	private String bookStoreName;

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

	private BookStore bookStore;

	public void initialize() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				@SuppressWarnings("unused")
				Stage stage = (Stage) nameField.getScene().getWindow();

				try (ConnectionDao connectionDao = new ConnectionDao()) {
					BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl(connectionDao);

					bookStore = bookStoreDaoImpl.getBookStoreByName(bookStoreName);

					Platform.runLater(new Runnable() {

						@Override
						public void run() {

							nameField.setText(bookStore.getName());
							urlField.setText(bookStore.getUrl());
							nameTagField.setText(bookStore.getNameTag());
							priceTagField.setText(bookStore.getPriceTag());
							nextTagField.setText(bookStore.getNextTag());
							authorTagField.setText(bookStore.getAuthorTag());
							tagsTagField.setText(bookStore.getTagsTag());
						}

					});
				} catch (SQLException e) {
					LOGGER.error("Can't close database connection");
				}
			}

		}).start();

	}

	@FXML
	private void handleOk() {
		if (nameField.getText().equals("") || urlField.getText().equals("") || nameTagField.getText().equals("")
				|| priceTagField.getText().equals("") || nextTagField.getText().equals("")|| authorTagField.getText().equals("")) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Empty fields");
			alert.setContentText("Ooops, there was an error!");

			alert.showAndWait();
		} else {

			BookStore bookStore = new BookStore(nameField.getText(), urlField.getText(), nameTagField.getText(),
					priceTagField.getText(), nextTagField.getText(), priceValueField.getText(),authorTagField.getText(),tagsTagField.getText());

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

	@FXML
	private void handleCancel() {
		Stage thisStage = (Stage) nameField.getScene().getWindow();
		thisStage.close();
	}
}
