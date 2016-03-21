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
import com.jaunt.component.Hyperlink;



public class LibraryChecker {
	private static UserAgent userAgent = new UserAgent();
	
	public static List<Book> getFreeBooks(BookStore library) throws ResponseException, NotFound{
		List<Book> bookList = new ArrayList<>();
		String url = library.getUrl();	
		boolean flag = false;
		while(!flag) {		
			try {		
				bookList.addAll(getFromSubSite(library,url));
				String link = userAgent.doc.findFirst(library.getNextTag()).outerHTML();
				link = link.substring(link.indexOf("http"));
				link = link.substring(0,link.indexOf("\">"));
				url= link;
			
		} catch(ResponseException | NotFound nf){
			nf.printStackTrace();
			flag=true;
		}
		}
	
				
		return bookList;
	}
	
	private static List<Book> getFromSubSite(BookStore library,String url) throws NotFound, ResponseException {
		userAgent.visit(url);
		List<Book> subsiteBookList = new ArrayList<>();
		Elements links = userAgent.doc.findFirst(library.getTag()).findEach(library.getContainer());
		for(Element element : links)
		{		
			
			if(element.findFirst(library.getPriceTag()).innerText().contains("0,00")||element.findFirst(library.getPriceTag()).innerText().contains("For Free"))
			{
			subsiteBookList.add(new Book(element.findFirst(library.getNameTag()).innerText(),library.getName()));
			System.out.println(element.findFirst(library.getNameTag()).innerText());
			}
		}
		return subsiteBookList;
	}
}
