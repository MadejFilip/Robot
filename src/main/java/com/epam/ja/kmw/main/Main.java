package com.epam.ja.kmw.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(final String... args) {

		// Set up a simple configuration that logs on the console.

		logger.trace("Starting our great robot application.");
		System.out.println("BUŁUNIA"!);
	}
}
