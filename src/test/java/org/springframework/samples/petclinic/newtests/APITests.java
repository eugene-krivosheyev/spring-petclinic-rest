package org.springframework.samples.petclinic.newtests;

import org.junit.jupiter.api.*;


import java.io.IOException;
import java.sql.*;

import static io.restassured.RestAssured.when;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class APITests extends Owner {

	private static Connection connection;

	@BeforeAll
	public static void connect() throws SQLException {
		connection = DriverManager.getConnection(
			"jdbc:postgresql://localhost:5432/petclinic",
			"petclinic",
			"petclinic"
		);
	}

	@AfterAll
	public static void disconnect() throws SQLException, IOException {
		connection.close();
	}

	@BeforeEach
	void setup() throws SQLException {

		PreparedStatement sql = connection.prepareStatement(
			"INSERT INTO owners(first_name, last_name) VALUES(?, ?)"
		);
		sql.setString(2, setLast_name(randomAlphabetic(3)));
		sql.setString(1, setFist_name(randomAlphabetic(3)));

		sql.executeUpdate();

	}

	@AfterEach
	public void setDown() throws SQLException {
		PreparedStatement sql = connection.prepareStatement(
			"DELETE FROM owners WHERE last_name = ?"
		);
		sql.setString(1, getLastName());
		sql.executeUpdate();
	}

	@Test
	public void getOwners() throws SQLException {

		when()
			.get("/owners?lastName=" + getLastName())
			.then()
			.statusCode(200)
			.body("id", is(notNullValue()),
				"firstName", is(getFirstName()),
				"lastName", is(getLastName()));
	}

	/*	PreparedStatement sql = connection.prepareStatement(
			"INSERT INTO owners(first_name, last_name, address, city, telephone) VALUES(?, ?, ?, ?,?)",
			Statement.RETURN_GENERATED_KEYS
		);
		sql.setString(1, getCountryName());
		sql.executeUpdate();

		ResultSet keys = sql.getGeneratedKeys();
		assertThat(keys.next(), is(true));
		setId(keys.getInt("id"));
	}

	@AfterEach
	public void setDown() throws SQLException {
		PreparedStatement sql = connection.prepareStatement(
			"DELETE FROM country WHERE country_name = ?"
		);
		sql.setString(1, getCountryName());
		sql.executeUpdate();
	}*/



}
