package org.restassured.petclinic.owner;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;

public class OwnerControllerTests {

	private static Connection connection;

	@BeforeAll
	public void connect() throws SQLException {
		connection = DriverManager.getConnection(
			"jdbc:postgresql://localhost:5432/petclinic",
			"petclinic",
			"petclinic"
		);
	}

	@AfterAll
	public static void disconnect() throws SQLException {
		connection.close();
	}

	@Nested
	@DisplayName("Tests for method POST /owners")
	class createOwnersTest {

	}
}
