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

/**
 * @author filipm Provides functions needed to check libraries sites and
 *         download proper books from those sites.
 */
public class LibraryChecker {
	private UserAgent userAgent;
	private int counter;
	private List<Book> bookList;
	private final BookStore library;

	public static final Logger LOGGER = LogManager.getLogger(LibraryChecker.class);

	/**
	 * Creates object of a LibraryChecker class and initializes it.
	 * 
	 * @param library
	 *            BookStore object storing informations about specific library.
	 */
	public LibraryChecker(BookStore library) {
		this.counter = 0;
		this.library = library;
		this.bookList = new LinkedList<>();
		this.userAgent = new UserAgent();
	}

	/**
	 * Returned object is a list of books. Method creates this list from books
	 * available on a library site.
	 * 
	 * @return list of books available on a library site.
	 */
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
	 * Returned object is a String storing URL of a library. Method validates
	 * sent link. If link is valid or operation fails it catches NotFound
	 * exception or ResponseException and returns message.
	 * 
	 * @param link
	 *            link that is validated.
	 * @return URL if link is correct or proper message if not.
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
	 * Downloads informations from library site about books. If this operation
	 * fails method catches NotFound exception and return false.
	 * 
	 * @param url
	 *            URL of a library site.
	 * @return true if operation succeed, false if not.
	 */
	private boolean getFromSubSite(String url) {
		System.out.println(url);
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
				text = text.replaceAll("&amp;", "&");
				if (!text.toLowerCase().contains(library.getType().toLowerCase()))
					continue;
				
				bookList.add(new Book(names.getElement(i).innerText().toString(), library,
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
