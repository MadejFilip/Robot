package com.epam.ja.kmw;

import java.io.IOException;

@SuppressWarnings("serial")
public class Servlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Main.main();
	}
}
