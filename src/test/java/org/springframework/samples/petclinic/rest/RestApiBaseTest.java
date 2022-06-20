package org.springframework.samples.petclinic.rest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RestApiBaseTest {

	private static String DB_URL = "jdbc:postgresql://localhost:5432/petclinic";
	private static String DB_USER = "petclinic";
	private static String DB_PASSWORD = "petclinic";

	public static Connection connection;

	@BeforeAll
	static void beforeAll() throws SQLException {
		connection = DriverManager.getConnection(
			DB_URL, DB_USER, DB_PASSWORD
		);
	}

	@AfterAll
	static void afterAll() throws SQLException {
		connection.close();
	}
}
