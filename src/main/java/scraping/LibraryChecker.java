package scraping;

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
	private static UserAgent userAgent = new UserAgent();
	private static int counter;

	public static final Logger LOGGER = LogManager.getLogger(LibraryChecker.class);

	public static List<Book> getFreeBooks(BookStore library) throws ResponseException, NotFound {
		counter = 0;
		List<Book> bookList = new LinkedList<>();
		String url = library.getUrl();
		boolean flag = false;
		while (counter < 100 && !flag) {
			
			try {
				bookList.addAll(getFromSubSite(library, url));
				String link = userAgent.doc.findFirst(library.getNextTag()).outerHTML();
				link = link.substring(link.indexOf("http"));
				link = link.substring(0, link.indexOf("\">"));
				url = link;
			} catch (IndexOutOfBoundsException | ResponseException ie) {
				LOGGER.error(ie.getMessage());
				flag = true;
			}
		}

		return bookList;
	}

	private static List<Book> getFromSubSite(BookStore library, String url) throws NotFound, ResponseException {
		LOGGER.info("Visit: " + url);
		userAgent.visit(url);
		List<Book> subsiteBookList = new LinkedList<>();
		Elements links = userAgent.doc.findFirst(library.getTag()).findEach(library.getContainer());
		for (Element element : links) {

			if (element.findFirst(library.getPriceTag()).innerText().contains("0,00")
					|| element.findFirst(library.getPriceTag()).innerText().contains("For Free")) {
				subsiteBookList.add(new Book(element.findFirst(library.getNameTag()).innerText(), library.getName()));
				counter++;
			}
		}
		return subsiteBookList;
	}
}
