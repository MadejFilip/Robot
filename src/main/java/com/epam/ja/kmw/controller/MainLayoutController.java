package com.epam.ja.kmw.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.BookDaoImpl;
import com.epam.ja.kmw.dao.impl.BookStoreDaoImpl;
import com.epam.ja.kmw.dao.impl.ConnectionDao;
import com.epam.ja.kmw.model.Book;
import com.epam.ja.kmw.model.BookStore;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author filipm Main controller in which user can chose operation that he want
 *         to execute.
 */
public class MainLayoutController {
	public static final Logger LOGGER = LogManager.getLogger(MainLayoutController.class);

	private Stage primaryStage;

	@FXML
	public TabPane tabPane;

	@FXML
	private DatePicker datePicker;

	@FXML
	Button showBooksByDateButton;

	@FXML
	Button editBookStoreButton;
	@FXML
	Button addBookStoreButton;

	/**
	 * Initialize main bookstore window. Fills all fields with proper
	 * informations (prints a list of all books contained in a specific
	 * bookstore).
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
	 * Opens new window responsible for editing selected bookstore.
	 */
	@FXML
	private void handleEditBookStore() {

		try {
			EditBookStoreController.tabPanel = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex())
					.getText();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FreeBookViewer.class.getResource("/EditBookStoreLayout.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();

			Scene scene = new Scene(anchorPane);
			Stage thisStage = (Stage) tabPane.getScene().getWindow();

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initOwner(thisStage);
			stage.initModality(Modality.WINDOW_MODAL);

			stage.showAndWait();

			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					tabPane.getTabs().clear();
					initialize();

				}

			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens new window responsible for adding new a bookstore.
	 */
	@FXML
	private void handleAddBookStore() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FreeBookViewer.class.getResource("/AddBookStoreLayout.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();

			Scene scene = new Scene(anchorPane);
			Stage stage = new Stage();
			stage.setScene(scene);

			// @SuppressWarnings("unused")
			// AddBookStoreController controller = loader.getController();

			stage.showAndWait();

			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					tabPane.getTabs().clear();
					initialize();

				}

			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Opens new window responsible for showing books with specific tags.
	 */
	@FXML
	private void handleShowBooks() {
		try {
			EditBookStoreController.tabPanel = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex())
					.getText();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FreeBookViewer.class.getResource("/ShowBooksLayout.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();

			Scene scene = new Scene(anchorPane);
			Stage thisStage = (Stage) tabPane.getScene().getWindow();

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initOwner(thisStage);
			stage.initModality(Modality.WINDOW_MODAL);

			stage.showAndWait();

			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					tabPane.getTabs().clear();
					initialize();

				}

			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes selected bookstore (calls the method delBookStore).
	 */
	@FXML
	private void handleDelBookStore() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try (ConnectionDao connectionDao = new ConnectionDao()) {
					BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl(connectionDao);

					BookStore bookStore = bookStoreDaoImpl
							.getBookStoreByName(tabPane.getSelectionModel().getSelectedItem().getText());

					bookStoreDaoImpl.delBookStore(bookStore.getId());

					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
						}
					});
				} catch (NullPointerException e) {
					LOGGER.trace("Empty BookStore list. Can't delete.");
				}
			}

		}).start();

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
