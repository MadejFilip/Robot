package com.epam.ja.kmw.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.epam.ja.kmw.model.BookStore;

/**
 * @author filipm This is implementation of BookStoreDao. Provides functions
 *         needed to operate on bookstores.
 */
public class BookStoreDaoImpl {

	SessionFactory sf = HibernateUtil.getSessionFactory();

	public boolean addBookStore(BookStore bookStore) {

		Session session = sf.openSession();
		session.beginTransaction();
		session.save(bookStore);
		session.getTransaction().commit();
		session.close();
		return true;

	}

	public boolean updateBookStore(BookStore bookStore) {

		Session session = sf.openSession();
		session.beginTransaction();

		session.update(bookStore);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	public boolean delBookStore(String nameBookStore) {
		System.out.println(nameBookStore);
		Session session = sf.openSession();
		session.beginTransaction();
		Query q = session.createQuery("delete from BookStore e where e.name=:name");
		q.setParameter("name", nameBookStore);
		q.executeUpdate();
		session.getTransaction().commit();
		session.close();
		return true;

	}

	public List<BookStore> getAllBooksStores() {

		Session session = sf.openSession();
		session.beginTransaction();
		List<BookStore> list = session.createCriteria(BookStore.class).list();
		session.getTransaction().commit();
		session.close();
		return list;

	}

	public BookStore getBookStoreByName(String bookStoreName) {

		Session session = sf.openSession();
		session.beginTransaction();
		Query q = session.createQuery("from BookStore e where e.name=:name");
		q.setParameter("name", bookStoreName);
		BookStore bookStore = (BookStore) q.uniqueResult();
		session.getTransaction().commit();
		session.close();
		if (bookStore == null) {
			return null;
		}
		return bookStore;
	}
}
