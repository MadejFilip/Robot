package com.epam.ja.kmw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.BookDaoImpl;
import com.epam.ja.kmw.dao.impl.BookStoreDaoImpl;
import com.epam.ja.kmw.dao.impl.ConnectionDao;
import com.epam.ja.kmw.model.Book;
import com.epam.ja.kmw.model.BookStore;
import com.epam.ja.kmw.model.Searcher;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShowBooksController {
	public static final Logger LOGGER = LogManager.getLogger(MainLayoutController.class);

	private Stage primaryStage;

	@FXML
	public TabPane tabPane;

	@FXML
	private DatePicker datePicker;

	@FXML
	private TextField tagsField;

	/**
	 * Initialize main bookstore window.
	 */
	public void initialize() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try (ConnectionDao connectionDao = new ConnectionDao()) {
					BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl(connectionDao);
					BookDaoImpl bookDao = new BookDaoImpl(connectionDao);

					for (BookStore bookStore : bookStoreDaoImpl.getAllBooksStores()) {

						ObservableList<String> listOfBooks = FXCollections.observableArrayList();

						ListView<String> listView = new ListView<String>();

						for (Book book : bookDao.getAllBooksForOneBookStore(bookStore.getName())) {

							listOfBooks.add(book.getTitle() + " *** " + book.getAuthor() + " *** " + book.getTags());
						}

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								listView.setItems(listOfBooks);
								Tab tab = new Tab(bookStore.getName());
								tab.setContent(listView);
								tabPane.getTabs().add(tab);
							}
						});
					}
				} catch (Exception e) {
					LOGGER.error("Can't close database connection");

				}
			}
		}).start();

	}

	/**
	 * Opens new window responsible for showing books with specific tags.
	 */
	@FXML
	private void handleShowBooks() {
		if (tagsField.getText().equals("") || datePicker.equals(null)) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Empty fields");
			alert.setContentText("Ooops, there was an error!");

			alert.showAndWait();
		} else {

			Searcher searcher = new Searcher(tagsField.getText(), datePicker.getValue());

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
		}

	}

	/**
	 * Closes main bookstore robot window.
	 */
	@FXML
	private void handleExit() {
		primaryStage.close();
	}

	/**
	 * Sets object which is an instance of Stage class.
	 * 
	 * @param primaryStage
	 *            object of the Stage class - main component of the bookstore
	 *            robot window..
	 */
	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * Returned object is an instance of Stage class.
	 * 
	 * @return Stage object - main component of the bookstore robot window.
	 */
	public Stage getStage() {
		return primaryStage;
	}

}