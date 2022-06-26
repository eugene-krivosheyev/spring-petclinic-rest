package apiTests.owners;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiOwnersTests extends OwnerPojo {

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
	public static void disconnect() throws SQLException {
		connection.close();
	}

	@BeforeEach
	void setup() throws SQLException {
		Random random = new Random();

		setId(random.nextInt());
		setFist_name(randomAlphabetic(3));
		setLast_name(randomAlphabetic(3));
		setAddress(randomAlphabetic(3));
		setCity(randomAlphabetic(3));
		setTelephone(String.valueOf(random.nextInt()));

		PreparedStatement sql = connection.prepareStatement(
			"INSERT INTO owners(id, first_name, last_name, address, city, telephone) VALUES(?,?,?,?,?,?)"
		);
		sql.setInt(1, getId());
		sql.setString(2, getFirstName());
		sql.setString(3, getLastName());
		sql.setString(4, getAddress());
		sql.setString(5, getCity());
		sql.setString(6, getTelephone());
		sql.executeUpdate();

	}

	/**
	 * Каскадное удаление строк из таблиц, связанных внешним ключом
	 */

	@AfterEach
	public void setDown() throws SQLException {
		PreparedStatement sqlVisits = connection.prepareStatement(
			" DELETE FROM visits WHERE id = ?"
		);
		sqlVisits.setInt(1, getId());
		sqlVisits.executeUpdate();

		PreparedStatement sqlPets = connection.prepareStatement(
			" DELETE FROM pets WHERE owner_id = ?"
		);
		sqlPets.setInt(1, getId());
		sqlPets.executeUpdate();

		PreparedStatement sql = connection.prepareStatement(
			" DELETE FROM owners WHERE id = ?"
		);
		sql.setInt(1, getId());
		sql.executeUpdate();
	}

	@DisplayName("Получить посетитея по фамилии")
	@Test
	public void getOwners() {
		when()
			.get("/owners?lastName=" + getLastName())
			.then()
			.statusCode(200)
			.body("", hasItem(hasEntry("id", getId())))
			.body(containsString(getLastName()));
	}

	@DisplayName("Добавить посетителя с визитом и питомцем")
	@Test
	public void postOwners() throws SQLException {
		setDown();
		given()
			.contentType("application/json")
			.body(
				"{\n"
					+ "  \"address\": \"" + getAddress() + "\",\n"
					+ "  \"city\": \"" + getCity() + "\",\n"
					+ "  \"firstName\": \"" + getFirstName() + "\",\n"
					+ "  \"id\": " + getId() + ",\n"
					+ "  \"lastName\": \"" + getLastName() + "\",\n"
					+ "  \"pets\": [\n"
					+ "    {\n"
					+ "      \"birthDate\": \"2000-06-26\",\n"
					+ "      \"id\": 11,\n"
					+ "      \"name\": \"string\",\n"
					+ "      \"visits\": [\n"
					+ "        {\n"
					+ "          \"date\": \"2000-06-26\",\n"
					+ "          \"description\": \"string\",\n"
					+ "          \"id\": 11\n"
					+ "        }\n"
					+ "      ]\n"
					+ "    }\n"
					+ "  ],\n"
					+ "  \"telephone\": \"" + getTelephone() + "\"\n"
					+ "}")
			.when()
			.post("/owners")
			.then().statusCode(201)
			.body("id", is(notNullValue()));
	}

	@DisplayName("Получить посетителя по Id")
	@Test
	public void getOwnersById() {
		when()
			.get("/owners/" + getId())
			.then()
			.statusCode(200)
			.body("id", is(getId()));
	}

	/**
	 * TO-BE
	 * Возвращает 200 вместо 400, BadRequest
	 */
	@DisplayName("Негативный тест получить посетителя без Id")
	@Disabled
	@Test
	public void getOwnersWithoutId() {
		when()
			.get("/owners/")
			.then()
			.statusCode(400);
	}

	/**
	 * TO-BE
	 * Должен быть метод PATCH
	 */
	@DisplayName("Добавить посетителю визит")
	@Disabled
	@Test
	public void postUpdateOwners() throws SQLException {
		setDown();
		given()
			.contentType("application/json")
			.body(
				"{\n"
					+ "  \"address\": \"" + getAddress() + "\",\n"
					+ "  \"city\": \"" + getCity() + "\",\n"
					+ "  \"firstName\": \"" + getFirstName() + "\",\n"
					+ "  \"id\": " + getId() + ",\n"
					+ "  \"lastName\": \"" + getLastName() + "\",\n"
					+ "  \"pets\": [\n"
					+ "    {\n"
					+ "      \"birthDate\": \"2000-06-20\",\n"
					+ "      \"id\": 88,\n"
					+ "      \"name\": \"stringName\",\n"
					+ "      \"visits\": [\n"
					+ "        {\n"
					+ "          \"date\": \"2000-06-20\",\n"
					+ "          \"description\": \"stringName\",\n"
					+ "          \"id\": 77\n"
					+ "        }\n"
					+ "      ]\n"
					+ "    }\n"
					+ "  ],\n"
					+ "  \"telephone\": \"" + getTelephone() + "\"\n"
					+ "}")
			.when()
			.post("/owners/" + getId())
			.then().statusCode(201)
			.body("id", is(notNullValue()));
	}
}

