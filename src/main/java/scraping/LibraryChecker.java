package scraping;

import java.util.ArrayList;
import java.util.List;

import com.epam.ja.kmw.model.Book;
import com.epam.ja.kmw.model.BookStore;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

public class LibraryChecker {
	private static final UserAgent userAgent = new UserAgent();

	public static List<Book> getFreeBooks(BookStore library) throws ResponseException, NotFound {
		List<Book> bookList = new ArrayList<>();
		String url = library.getUrl();
		boolean flag = false;
		while (!flag) {
			try {
				userAgent.doc = userAgent.visit(url);
				bookList.addAll(getFromSubSite(library));
				url = userAgent.doc.findFirst("<a class=\"next\">").getAt("href").toString();
				System.out.println(url);

			} catch (NotFound | ResponseException nf) {
				nf.printStackTrace();
				flag = true;
			}
		}

		return bookList;
	}

	private static List<Book> getFromSubSite(BookStore library) throws NotFound, ResponseException {

		List<Book> subsiteBookList = new ArrayList<>();
		Elements links = userAgent.doc.findFirst(library.getTag()).findEach(library.getContainer());
		for (Element element : links) {

			if (element.findFirst(library.getPriceTag()).innerText().contains("0,00"))
				subsiteBookList.add(new Book(element.getFirst(library.getNameTag()).innerText(), library.getName()));
			System.out.println(element.getFirst(library.getNameTag()).innerText());
		}
		return subsiteBookList;
	}
}
