package com.epam.ja.kmw.dao.impl;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.ja.kmw.dao.impl.ConnectionDao.CreateConnectionDelegate;

public class ConnectionDaoTest {
	static CreateConnectionDelegate modzajto;

	@BeforeMethod
	public void sadasdasdasdsadas() throws SQLException {
		modzajto = mock(CreateConnectionDelegate.class);
		doThrow(new SQLException()).when(modzajto).createConnectionDelegate();

	}

	@Test
	public void connectionDaoIsGood() throws SQLException {

		ConnectionDao connectionDao = new ConnectionDao();
		connectionDao.createConnectionDelegate = modzajto;
		connectionDao.methodCreateConnection();

		verify(modzajto).createConnectionDelegate();
	}

	@Test
	public void connectionDaoIsWrong() throws IOException {

		ConnectionDao connectionDao = new ConnectionDao();
		connectionDao.createConnectionDelegate = modzajto;

		assertThat(connectionDao.methodCreateConnection()).isFalse();

	}
}
