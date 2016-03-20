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
		Elements links = userAgent.doc.findFirst(library.getTag()).findEach("<li>");
		for(Element element : links)
		{		
			if(element.findFirst("<strong class=nprice>").getText().contains("0,00"))
			bookList.add(new Book(library.getName(),element.getFirst("<h3>").findFirst("<a>").getText()));
			
		}
	
				
		return bookList.toArray(new Book[bookList.size()]);
	}
}
