package com.epam.ja.kmw.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class BookTest {

	@Test
	public void testMethodEqualsForTwoDifferentObjects() {
		// given
		Book bookOne = new Book("wojna", "matrys", "adam", "tag");
		Book bookTwo = new Book("pokoj", "matrys", "adam", "tag");
		// when
		boolean equals = bookOne.equals(bookTwo);
		// then
		assertThat(equals).isFalse();
	}

	@Test
	public void testMethodEqualsForTwoEvenObjects() {
		// given
		Book bookOne = new Book("wojna", "matrys", "adam", "tag");
		Book bookTwo = new Book("wojna", "matrys", "adam", "tag");
		// when
		boolean equals = bookOne.equals(bookTwo);
		// then
		assertThat(equals).isTrue();
	}

	// maybe soft assert for hashcode
	@Test
	public void testIfHashCodeIsDifferentForTwoDifferObjects() {
		// given
		Book bookOne = new Book("wojna", "matrys", "adam", "tag");
		Book bookTwo = new Book("pokoj", "matrys", "adam", "tag");
		// when
		int bookOneHashCode = bookOne.hashCode();
		int bookTwoHashCode = bookTwo.hashCode();
		// then
		assertThat(bookOneHashCode).isNotEqualTo(bookTwoHashCode);
	}

	@Test
	public void testIfHashCodeIsSameForIdenticalObjects() {
		// given
		Book bookOne = new Book("wojna", "matrys", "adam", "tag");
		Book bookTwo = bookOne;
		// when
		int bookOneHashCode = bookOne.hashCode();
		int bookTwoHashCode = bookTwo.hashCode();
		// then
		assertThat(bookOneHashCode).isEqualTo(bookTwoHashCode);
	}
}
