package org.springframework.samples.petclinic.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RestApiBaseTest {

	static final String BASE_URI = "http://localhost";
	static final int PORT = 8080;
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/petclinic";
	private static final String DB_USER = "petclinic";
	private static final String DB_PASSWORD = "petclinic";

	static RequestSpecification requestSpec;
	public static Connection connection;

	@BeforeAll
	static void beforeAll() throws SQLException {
		requestSpec = new RequestSpecBuilder().
			setBaseUri(BASE_URI).
			setPort(PORT).
			build();

		connection = DriverManager.getConnection(
			DB_URL, DB_USER, DB_PASSWORD
		);
	}

	@AfterAll
	static void afterAll() throws SQLException {
		connection.close();
	}
}
