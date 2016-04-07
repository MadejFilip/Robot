package com.epam.ja.kmw.scraping;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.model.Book;
import com.epam.ja.kmw.model.BookStore;
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

	private String linkPropagator(String link) {
		String url = null;
		try {
			userAgent.visit(link);
			url = userAgent.doc.findFirst(library.getNextTag()).outerHTML();
			url = url.substring(url.indexOf("http"));
			url = url.substring(0, url.indexOf("\""));

		} catch (ResponseException | NotFound e) {
			LOGGER.trace("Can't find next subsite on :" + link);
		}
		return url;

	}

	private boolean getFromSubSite(String url) {
		try {
			userAgent.visit(url);
		} catch (ResponseException e) {
			LOGGER.trace("Can't get to: " + url);
			return false;
		}

		Elements names = userAgent.doc.findEvery(library.getNameTag());
		
		
		Elements author = userAgent.doc.findEvery(library.getAuthorTag());
		//String tags="<p class="item-keywords word-break">";
		System.out.println(library.getTagsTag());
		Elements tag = userAgent.doc.findEvery(library.getTagsTag());
		
		Elements prices = userAgent.doc.findEvery(library.getPriceTag());
		for (int i = 0; i < names.size(); i++) {
			try {

				if (prices.getElement(i).innerText().contains(library.getPriceValue())) {
					bookList.add(new Book(names.getElement(i).innerText(), library.getName(), author.getElement(i).innerText(), tag.getElement(i).innerText()));
					counter++;
					if (counter >= 100) {
						return false;
					}
				}
			} catch (NotFound e) {
				LOGGER.trace("Can't find Book on :");
				return false;
			}

		}

		return true;
	}
}
