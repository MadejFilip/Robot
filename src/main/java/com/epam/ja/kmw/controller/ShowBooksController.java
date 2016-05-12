package com.epam.ja.kmw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.ConnectionDao;
import com.epam.ja.kmw.dao.impl.SearcherDaoImpl;
import com.epam.ja.kmw.model.Book;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

/**
 * @author filipm This is controller which allows user showing downloaded books
 *         by GUI.
 */
public class ShowBooksController {
	public static final Logger LOGGER = LogManager.getLogger(MainLayoutController.class);

	@FXML
	public TabPane tabPane;

	@FXML
	private TextField tagsField;

	/**
	 * Initialize main bookstore window.
	 */
	public void initialize() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				ObservableList<String> listOfBooks = FXCollections.observableArrayList();

				ListView<String> listView = new ListView<String>();

				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						listView.setItems(listOfBooks);
						Tab tab = new Tab();
						tab.setContent(listView);
						tabPane.getTabs().add(tab);
					}
				});

			}
		}).start();

	}

	/**
	 * Opens new window responsible for showing books with specific tags.
	 */
	@FXML
	private void handleShowBooks() {
		if (tagsField.getText().equals("")) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Empty fields");
			alert.setContentText("Ooops, there was an error!");

			alert.showAndWait();
		} else {

			new Thread(new Runnable() {

				@Override
				public void run() {

					try (ConnectionDao connectionDao = new ConnectionDao()) {

						SearcherDaoImpl searcherDaoImpl = new SearcherDaoImpl(connectionDao);

						searcherDaoImpl.getBooksByTag(tagsField.getText());

						ObservableList<String> listOfBooks = FXCollections.observableArrayList();

						ListView<String> listView = new ListView<String>();

						for (Book book : searcherDaoImpl.getBooksByTag(tagsField.getText())) {

							listOfBooks.add(book.getTitle() + " *** " + book.getAuthor() + " *** " + book.getTags());
						}
						if(listOfBooks.isEmpty())
						{
							listOfBooks.add("Nie znaleziono żadnych wyników wyszukiwania");
						}
						Platform.runLater(new Runnable() {

							@Override
							public void run() {

								listView.setItems(listOfBooks);
								Tab tab = new Tab(tagsField.getText() + " ");
								tab.setContent(listView);
								tabPane.getTabs().add(tab);
							}

						});
					} catch (Exception e) {
						LOGGER.error("Can't close database connection");
					}
				}

			}).start();
		}

	}
}