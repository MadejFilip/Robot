package com.epam.ja.kmw.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import com.epam.ja.kmw.model.Book;
import com.epam.ja.kmw.model.BookStore;

/**
 * @author filipm This is implementation of BookDao. Provides functions needed
 *         to operate on books.
 */
public class BookDaoImpl {
	SessionFactory sf = HibernateUtil.getSessionFactory();

	public boolean addAllBooks(List<Book> listOfBooks) {

		StatelessSession session = sf.openStatelessSession();
		Transaction tx = session.beginTransaction();
		for (int i = 0; i < listOfBooks.size(); i++) {
			session.insert(listOfBooks.get(i));
		}
		tx.commit();
		session.close();
		return true;
	}

	/**
	 * 
	 * @param bookStore
	 * @return
	 */
	public List<Book> getAllBooksByBookStore(BookStore bookStore) {
		Session session = sf.openSession();
		session.beginTransaction();
		Query query = session.createQuery("select b from Book b where b.bookStore=:bookstore_id");
		query.setInteger("bookstore_id", bookStore.getId());
		List<Book> lists = query.list();
		session.getTransaction().commit();
		session.close();
		return lists;

	}
}
