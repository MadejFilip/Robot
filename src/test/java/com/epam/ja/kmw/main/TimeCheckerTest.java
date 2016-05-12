package com.epam.ja.kmw.main;

import java.util.Date;

import org.testng.annotations.Test;

public class TimeCheckerTest {

	@Test
	public void TestTimeChecker() {
		Date curDate = new Date();
		TimeChecker dao = new TimeChecker("12");
		boolean runStatus = dao.delegatorRun.DelegatorRun();
		// softAssert.assertTrue(booksOne.equals(booksTwo));

	}
}
