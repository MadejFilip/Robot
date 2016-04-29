package com.epam.ja.kmw.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.Test;

import com.epam.ja.kmw.model.Book;

public class BookDaoImplTest {
	// private SoftAssert softAssert = new SoftAssert();

	@Test
	public void testsMethodAddBook() {
		// given
		Book bookOne = new Book("wojna", "matrys", "adam", "tag");
		ConnectionDao connectionDao = new ConnectionDao();
		BookDaoImpl bookDaoImpl = new BookDaoImpl(connectionDao);
		boolean ifBookAdded = bookDaoImpl.addBook(bookOne);
		// when
		boolean expectedValue = true;
		Book bookTwo = new Book("wojna", "matrys", "adam", "tag");
		// then
		assert bookOne.equals(bookTwo) && ifBookAdded == expectedValue;
		// softAssert.assertTrue(bookOne.equals(bookTwo));
		// softAssert.assertEquals(ifBookAdded, expectedValue);
	}

	@Test
	public void testsMethodAddAllBooks() {
		// given
		ArrayList<Book> booksOne = new ArrayList<>();
		booksOne.addAll(Arrays.asList(new Book("wojna", "matrys", "adam", "tag"),
				new Book("pokoj", "matrys", "adam", "tag"), new Book("romansidlo", "matrys", "adam", "tag")));
		ConnectionDao connectionDao = new ConnectionDao();
		BookDaoImpl bookDaoImpl = new BookDaoImpl(connectionDao);
		boolean ifBookAdded = bookDaoImpl.addAllBooks(booksOne);
		// when
		boolean expectedValue = true;
		ArrayList<Book> booksTwo = new ArrayList<>();
		booksTwo.addAll(Arrays.asList(new Book("wojna", "matrys", "adam", "tag"),
				new Book("pokoj", "matrys", "adam", "tag"), new Book("romansidlo", "matrys", "adam", "tag")));
		// then
		assert booksOne.equals(booksTwo) && ifBookAdded == expectedValue;
	}

	@Test
	public void testsMethodUpdateBook() {
		// given
		Book bookOne = new Book("wojna", "matrys", "adam", "tag");
		ConnectionDao connectionDao = new ConnectionDao();
		BookDaoImpl bookDaoImpl = new BookDaoImpl(connectionDao);
		boolean ifBookUpdated = bookDaoImpl.updateBook(bookOne);
		// when
		boolean expectedValue = true;
		Book bookTwo = new Book("wojna", "matrys", "adam", "tag");
		// then
		assert bookOne.equals(bookTwo) && ifBookUpdated == expectedValue;
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
