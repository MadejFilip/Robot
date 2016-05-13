package com.epam.ja.kmw.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.epam.ja.kmw.model.Book;

/**
 * @author filipm Provides function needed to search books by tags.
 */
public class SearcherDaoImpl {

	SessionFactory sf = HibernateUtil.getSessionFactory();

	public List<Book> getBooksByTag(String searchedTag) {

		Session session = sf.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Book b where b.tags like :tags");

		List<Book> book = query.setParameter("tags", "%" + searchedTag + "%").list();

		session.getTransaction().commit();
		session.close();
		return book;

	}

}