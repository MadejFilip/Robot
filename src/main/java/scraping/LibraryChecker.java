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
	
	public static Book[] getFreeBooks(BookStore library) throws ResponseException, NotFound{
		List<Book> bookList = new ArrayList<>();
		
		userAgent.visit(library.getUrl());
		Elements links = userAgent.doc.findFirst(library.getTag()).findEach(library.getContainer());
		for(Element element : links)
		{		
			
			if(element.findFirst(library.getPriceTag()).innerText().contains("0,00"))
			bookList.add(new Book(element.getFirst(library.getNameTag()).innerText(),library.getName()));
			
		}
	
				
		return bookList.toArray(new Book[bookList.size()]);
	}
}
