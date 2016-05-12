package com.epam.ja.kmw.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BookTest {

	@DataProvider(name = "booksValues")
	public Object[][] giveData() {
		return new Object[][] { { "wojna", "matrys", "adam", "romance" }, { "pokoj", "empik", "adam", "tag" },
				{ "historia polski", "ekiosk", "filip", "tag" }, { "drags", "empik", "filip", "narcotics" } };
	}

	public void f(String key, String value) {
		System.out.println("PARAMETRY: " + key + " - " + value);
	}

	@Test(dataProvider = "booksValues")
	public void testMethodEqualsForTwoDifferentObjects(String bookTitle, String bookStore, String bookAauthor,
			String bookTag) {
		// given
		Book bookOne = new Book(bookTitle, bookStore, bookAauthor, bookTag);
		Book bookTwo = new Book("jak dzialaja narkotyki", "matrys", "adam", "tag");
		// when
		boolean equals = bookOne.equals(bookTwo);
		// then
		assertThat(equals).isFalse();
	}

	@Test(dataProvider = "booksValues")
	public void testMethodEqualsForTwoEvenObjects(String bookTitle, String bookStore, String bookAauthor,
			String bookTag) {
		// given
		Book bookOne = new Book(bookTitle, bookStore, bookAauthor, bookTag);
		Book bookTwo = new Book(bookTitle, bookStore, bookAauthor, bookTag);
		// when
		boolean equals = bookOne.equals(bookTwo);
		// then
		assertThat(equals).isTrue();
	}

	// maybe soft assert for hashcode
	@Test(dataProvider = "booksValues")
	public void testIfHashCodeIsDifferentForTwoDifferObjects(String bookTitle, String bookStore, String bookAauthor,
			String bookTag) {
		// given
		Book bookOne = new Book(bookTitle, bookStore, bookAauthor, bookTag);
		Book bookTwo = new Book("jak dzialaja narkotyki", "matrys", "adam", "tag");
		// when
		int bookOneHashCode = bookOne.hashCode();
		int bookTwoHashCode = bookTwo.hashCode();
		// then
		assertThat(bookOneHashCode).isNotEqualTo(bookTwoHashCode);
	}

	@Test(dataProvider = "booksValues")
	public void testIfHashCodeIsSameForIdenticalObjects(String bookTitle, String bookStore, String bookAauthor,
			String bookTag) {
		// given
		Book bookOne = new Book(bookTitle, bookStore, bookAauthor, bookTag);
		Book bookTwo = bookOne;
		// when
		int bookOneHashCode = bookOne.hashCode();
		int bookTwoHashCode = bookTwo.hashCode();
		// then
		assertThat(bookOneHashCode).isEqualTo(bookTwoHashCode);
	}
}