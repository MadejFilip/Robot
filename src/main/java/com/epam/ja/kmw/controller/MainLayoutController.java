package com.epam.ja.kmw.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.dao.impl.BookDaoImpl;
import com.epam.ja.kmw.dao.impl.BookStoreDaoImpl;
import com.epam.ja.kmw.model.Book;
import com.epam.ja.kmw.model.BookStore;
import com.epam.ja.kmw.viewer.FreeBookViewer;

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

public class MainLayoutController {
	public static final Logger LOGGER = LogManager.getLogger(MainLayoutController.class);
	@FXML
	private TabPane tabPane;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Button showBooksByDateButton;
	@FXML
	private Button editBookStoreButton;
	@FXML
	private Button addBookStoreButton;

	public void initialize() {

		showBooksByDateButton.setDisable(true);

		new Thread(new Runnable() {

			@Override
			public void run() {

				try(BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl() ;
						BookDaoImpl bookDao = new BookDaoImpl()) {

				bookStoreDaoImpl.createConnection();
				bookStoreDaoImpl.createTable();

				for (BookStore bookStore : bookStoreDaoImpl.getAllBooksStores()) {

					ObservableList<String> listOfBooks = FXCollections.observableArrayList();

					ListView<String> listView = new ListView<String>();

					
					bookDao.createConnection();
					bookDao.createTable();
					for (Book book : bookDao.getAllBooksForOneBookStore(bookStore.getName())) {
						listOfBooks.add(book.getTitle());
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
				}catch(Exception e){
					LOGGER.error("Can't close database connection");

				}
			}
		}).start();

	}

	@FXML
	private void handleEditBookStore() {

		AnchorPane anchorPane = new AnchorPane();

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FreeBookViewer.class.getResource("/EditBookStoreLayout.fxml"));
			anchorPane = (AnchorPane) loader.load();

			Scene scene = new Scene(anchorPane);
			Stage thisStage = (Stage) tabPane.getScene().getWindow();
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initOwner(thisStage);
			stage.initModality(Modality.WINDOW_MODAL);

			@SuppressWarnings("unused")
			EditBookStoreController controller = loader.getController();
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleAddBookStore() {

		AnchorPane anchorPane = new AnchorPane();

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FreeBookViewer.class.getResource("/AddBookStoreLayout.fxml"));
			anchorPane = (AnchorPane) loader.load();

			Scene scene = new Scene(anchorPane);
			Stage stage = new Stage();
			stage.setScene(scene);

			@SuppressWarnings("unused")
			AddBookStoreController controller = loader.getController();

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

	@FXML
	private void handleShowBooks() {

	}

	@FXML
	private void handleDelBookStore() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try(BookStoreDaoImpl bookStoreDaoImpl = new BookStoreDaoImpl();
						BookDaoImpl bookDao = new BookDaoImpl();){
				

				bookStoreDaoImpl.createConnection();
				bookStoreDaoImpl.createTable();

				
				BookStore bookStore = bookStoreDaoImpl
						.getBookStoreByName(tabPane.getSelectionModel().getSelectedItem().getText());
				

				bookStoreDaoImpl.delBookStore(bookStore.getId());

			
				
				bookDao.createConnection();
				bookDao.createTable();


				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
					}
				});
				}catch(NullPointerException e) {
					LOGGER.trace("Empty BookStore list. Can't delete.");
				}catch(Exception e) {
					LOGGER.error("Can't close database connection");
				}
			}
			

		}).start();
		

	}

	@FXML
	private void handleExit() {
		
	}

}
