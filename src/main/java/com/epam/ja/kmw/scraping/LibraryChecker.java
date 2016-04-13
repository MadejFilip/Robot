package com.epam.ja.kmw.scraping;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.model.Book;
import com.epam.ja.kmw.model.BookStore;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

public class LibraryChecker {
	private UserAgent userAgent;
	private int counter;
	private List<Book> bookList;
	private final BookStore library;

	public static final Logger LOGGER = LogManager.getLogger(LibraryChecker.class);

	public LibraryChecker(BookStore library) {
		this.counter = 0;
		this.library = library;
		this.bookList = new LinkedList<>();
		this.userAgent = new UserAgent();
	}

	public List<Book> getFreeBooks() {
		String url = library.getUrl();

		while (getFromSubSite(url)) {
			url = linkPropagator(url);
			if (url == null) {
				break;
			}
		}

		return bookList;
	}

	/**
	 * @param link
	 * @return String
	 * propaguje linki dodany zostal warunek:
	 * if (!library.getNextTag().equals("---")) {
	 */
	private String linkPropagator(String link) {
		String url = null;
		try {
			userAgent.visit(link);
			if (!library.getNextTag().equals("---")) {
				url = userAgent.doc.findFirst(library.getNextTag()).outerHTML();
				url = url.substring(url.indexOf("http"));
				url = url.substring(0, url.indexOf("\""));
			}

		} catch (ResponseException | NotFound e) {
			LOGGER.trace("Can't find next subsite on :" + link);
		}
		return url;

	}
	
	/**
	 * @param url
	 * @return boolean
	 * Metoda pobierajaca dane z strony, 
	 * dodane zostalo do niej author, tag, 
	 * oraz uzuniete zostalo Price, poniewaz nie byla potrzebna.
	 */
	private boolean getFromSubSite(String url) {
		try {
			userAgent.visit(url);
		} catch (ResponseException e) {
			LOGGER.trace("Can't get to: " + url);
			return false;
		}

		Elements names = userAgent.doc.findEvery(library.getNameTag());
		Elements author = userAgent.doc.findEvery(library.getAuthorTag());
		Elements tag = userAgent.doc.findEvery(library.getTagsTag());
		
		for (int i = 0; i < names.size(); i++) {
			try {
				
				String text = tag.getElement(i).innerText();
				text =text.replaceAll("&amp;", "&");
				if (!text.toLowerCase().contains(library.getType().toLowerCase()))
					continue;
				
				System.out.println(text);
				bookList.add(new Book (names.getElement(i).innerText(), library.getName(),
						author.getElement(i).innerText(), tag.getElement(i).innerText()));
				counter++;
				if (counter >= 100) {
					return false;

				}
			} catch (NotFound e) {
				LOGGER.trace("Can't find Book on :");
				return false;
			}

		}
		return true;
	}
}
