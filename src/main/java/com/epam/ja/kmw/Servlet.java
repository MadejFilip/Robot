package com.epam.ja.kmw;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Team Bula
 * @version 1.0
 *  Class Servlet
 */
@SuppressWarnings("serial")
public class Servlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Main.main();
	}
}
