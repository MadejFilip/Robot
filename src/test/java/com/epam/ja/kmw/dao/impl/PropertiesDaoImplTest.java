package com.epam.ja.kmw.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.ja.kmw.dao.impl.PropertiesDaoImpl.CreateTablePropertiesDelegation;
import com.epam.ja.kmw.model.Properties;

public class PropertiesDaoImplTest {
	static CreateTablePropertiesDelegation modzajto;

	@BeforeMethod
	public void sadasdasdasdsadas() throws SQLException {
		modzajto = mock(CreateTablePropertiesDelegation.class);
		doThrow(new SQLException()).when(modzajto).createTablePropertiesDelegation();

	}

	@Test
	public void createTablePropertiesIsGood() throws SQLException {

		PropertiesDaoImpl propertiesDaoImpl = new PropertiesDaoImpl(new ConnectionDao());
		propertiesDaoImpl.createTablePropertiesDelegation = modzajto;
		propertiesDaoImpl.methodCreateTableProperties();

		verify(modzajto).createTablePropertiesDelegation();
	}

	@Test
	public void createTablePropertiesIsWrong() throws SQLException {

		PropertiesDaoImpl propertiesDaoImpl = new PropertiesDaoImpl(new ConnectionDao());
		propertiesDaoImpl.createTablePropertiesDelegation = modzajto;

		assertThat(propertiesDaoImpl.methodCreateTableProperties()).isFalse();

	}

	@Test
	public void getPropertiesIsGood() throws IOException {
		PropertiesDaoImpl propertiesDaoImpl = new PropertiesDaoImpl(new ConnectionDao());

		assertThat(propertiesDaoImpl.getProperties()).isNotNull();

	}

	@Test
	public void getPropertiesIsNotGood() {
		PropertiesDaoImpl modzajto1 = mock(PropertiesDaoImpl.class);

	}

	@Test
	public void upgradeIsGood() throws IOException {
		PropertiesDaoImpl propertiesDaoImpl = new PropertiesDaoImpl(new ConnectionDao());

		assertThat(propertiesDaoImpl.updateProperties(new Properties("aa", 1))).isTrue();

	}

}
