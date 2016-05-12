package com.epam.ja.kmw.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.epam.ja.kmw.model.Book;

public class BookDaoImplTest {

	private SoftAssert softAssert = new SoftAssert();
	private static final Logger LOGGER = LogManager.getLogger(BookDaoImpl.class);
	private ConnectionDao connectionDao;

	@BeforeMethod
	public void createTestTable() {
		String createBooksTableQuery = "CREATE TABLE IF NOT EXISTS BooksTest (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " Title varchar(255), BookStore varchar(255), add_date datetime default current_datetime, Author varchar(255),  Tags varchar(255) )";
		try {

			connectionDao.getStatement().execute(createBooksTableQuery);
			LOGGER.info("Created BookTest database");

		} catch (SQLException e) {
			LOGGER.error("Fail to create a table 'BookTest' in database. :" + e.getMessage());
		}
	}

	@DataProvider(name = "booksValues")
	public Object[][] giveData1() {
		return new Object[][] { { "wojna", "matrys", "adam", "romans" }, { "pokoj", "empik", "adam", "tag" },
				{ "historia polski", "matrys", "filip", "tag" }, { "drags", "empik", "filip", "narcotics" } };
	}

	@Test(dataProvider = "booksValues")
	public void testsMethodAddBook(String bookTitle, String bookStore, String bookAauthor, String bookTag) {
		// given
		Book bookOne = new Book(bookTitle, bookStore, bookAauthor, bookTag);
		ConnectionDao connectionDao = new ConnectionDao();
		BookDaoImpl bookDaoImpl = new BookDaoImpl(connectionDao);
		boolean ifBookAdded = bookDaoImpl.addBook(bookOne);
		// when
		boolean expectedValue = true;
		Book bookTwo = new Book(bookTitle, bookStore, bookAauthor, bookTag);
		// then
		softAssert.assertTrue(bookOne.equals(bookTwo));
		softAssert.assertEquals(ifBookAdded, expectedValue);
		softAssert.assertAll();
	}

	@DataProvider(name = "bookListsValues")
	public Object[][] giveData2() {
		ArrayList<Book> bookList1 = new ArrayList<>();
		ArrayList<Book> bookList2 = new ArrayList<>();
		ArrayList<Book> bookList3 = new ArrayList<>();

		bookList1.addAll(Arrays.asList(new Book("wojna", "matrys", "adam", "romance"),
				new Book("pokoj", "ekiosk", "filip", "tag"), new Book("sexinthecity", "empik", "adam", "sex")));
		bookList2.addAll(Arrays.asList(new Book("wojna", "matrys", "adam", ""),
				new Book("pokoj", "empik", "adam", "tag"), new Book("romansidlo", "gandalf", "adam", "romance")));
		bookList3.addAll(Arrays.asList(new Book("madmax", "gandalf", "adam", "sciencefiction"),
				new Book("pokoj", "matrys", "adam", "tag"), new Book("biologia", "ekiosk", "adam", "nauka")));

		return new Object[][] { { bookList1 }, { bookList2 }, { bookList3 } };
	}

	@Test(dataProvider = "bookListsValues")
	public void testsMethodAddAllBooks(ArrayList<Book> list) {
		// given
		ArrayList<Book> booksOne = list;
		ConnectionDao connectionDao = new ConnectionDao();
		BookDaoImpl bookDaoImpl = new BookDaoImpl(connectionDao);
		boolean ifBookAdded = bookDaoImpl.addAllBooks(booksOne);
		// when
		boolean expectedValue = true;
		ArrayList<Book> booksTwo = list;
		// then
		softAssert.assertTrue(booksOne.equals(booksTwo));
		softAssert.assertEquals(ifBookAdded, expectedValue);
		softAssert.assertAll();
	}

	@Test(dataProvider = "booksValues")
	public void testsMethodUpdateBook(String bookTitle, String bookStore, String bookAauthor, String bookTag) {
		// given
		Book bookOne = new Book(bookTitle, bookStore, bookAauthor, bookTag);
		ConnectionDao connectionDao = new ConnectionDao();
		BookDaoImpl bookDaoImpl = new BookDaoImpl(connectionDao);
		boolean ifBookUpdated = bookDaoImpl.updateBook(bookOne);
		// when
		boolean expectedValue = true;
		Book bookTwo = new Book(bookTitle, bookStore, bookAauthor, bookTag);
		// then
		softAssert.assertTrue(bookOne.equals(bookTwo));
		softAssert.assertEquals(ifBookUpdated, expectedValue);
		softAssert.assertAll();
	}

	@Test
	public void testsMethodDelBook() {
		// given
		// ArrayList<Book> booksOne = new ArrayList<>();
		// booksOne.addAll(Arrays.asList(new Book("wojna", "matrys"), new
		// Book("pokoj", "matrys"),
		// new Book("romansidlo", "matrys")));

		ConnectionDao connectionDao = new ConnectionDao();
		BookDaoImpl bookDaoImpl = new BookDaoImpl(connectionDao);
		// bookDaoImpl.addAllBooks(booksOne);
		boolean ifBookDeleted = bookDaoImpl.delBook(207);
		// when
		boolean expectedValue = false;

		// ArrayList<Book> booksTwo = new ArrayList<>();
		// booksOne.addAll(Arrays.asList(new Book("wojna", "matrys"), new
		// Book("pokoj", "matrys")));
		// then
		assert ifBookDeleted == expectedValue;
	}

	@Test
	public void testsMethodGetAllBooks() {
		// given
		ArrayList<Book> booksOne = new ArrayList<>();
		ConnectionDao connectionDao = new ConnectionDao();
		BookDaoImpl bookDaoImpl = new BookDaoImpl(connectionDao);
		booksOne = (ArrayList<Book>) bookDaoImpl.getAllBooks();
		// when
		ArrayList<Book> emptyBooksList = null;
		// then
		assert booksOne != emptyBooksList;
	}

	// @Test
	// public void testsMethodGetBookByID() {
	// // given
	// ConnectionDao connectionDao = new ConnectionDao();
	// BookDaoImpl bookDaoImpl = new BookDaoImpl(connectionDao);
	// Book bookDwonloaded = bookDaoImpl.getBookById(2);
	// // when
	// bookDaoImpl.addBook(new Book("wojna", "matrys", "adam", "tag"));
	// Book bookExpected = new Book("wojna", "matrys", "adam", "tag");
	// System.out.println(bookDaoImpl.getAllBooks());
	// System.out.println(" CHUJJJJJ1111111111111" +bookDwonloaded);
	// System.out.println(" CHUJJJJJ" +bookExpected);
	// // then
	// assert bookDwonloaded.equals(bookExpected);
	// }

}
