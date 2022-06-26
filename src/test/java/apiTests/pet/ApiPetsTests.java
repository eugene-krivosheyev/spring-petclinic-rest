package apiTests.pet;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class ApiPetsTests extends PetsPojo {
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
		Random random = new Random();
		LocalDate date = LocalDate.now();

		Statement sqlOwner = connection.createStatement();
		ResultSet resultSet = sqlOwner.executeQuery("SELECT * from owners OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY;");
		while (resultSet.next()) {
			setOwner_id(resultSet.getInt(1));
		}

		PetsPojo pet = new PetsPojo();
		setId(random.nextInt());
		setName(randomAlphabetic(3));
		setBirthDate(date);

		PreparedStatement sql = connection.prepareStatement(
			"INSERT INTO PETS(id, name, birth_date, owner_id) VALUES(?,?,?,?)"
		);
		sql.setInt(1, getId());
		sql.setString(2, getName());
		sql.setDate(3, Date.valueOf(getBirthDate()));
		sql.setInt(4, getOwner_id());
		sql.executeUpdate();

	}

	@AfterEach
	public void setDown() throws SQLException {
		PreparedStatement sqlVisits = connection.prepareStatement(
			" DELETE FROM visits WHERE pet_id = ?"
		);
		sqlVisits.setInt(1, getId());
		sqlVisits.executeUpdate();

		PreparedStatement sqlPets = connection.prepareStatement(
			" DELETE FROM pets WHERE id = ?"
		);
		sqlPets.setInt(1, getId());
		sqlPets.executeUpdate();
	}

	@DisplayName("Получить питомцев посетителя по Id")
	@Test
	public void getOwners() {
		when()
			.get("/owners/" + getOwner_id() + "/pets")
			.then()
			.statusCode(200)
			.body(containsString(getName()))
			.body("", hasItem(hasEntry("id", getId())));
	}

	/**
	 * Пустое тело ответа
	 * TO-BE тело ответа добавленной записи
	 */
	@Disabled
	@DisplayName("Добавить питомцев посетителю по его Id")
	@Test
	public void postPets() throws SQLException {
		setDown();
		given()
			.contentType("application/json")
			.body(
				"{\n" +
					"  \"birthDate\": \"" + getBirthDate().toString() + "\",\n" +
					"  \"id\": " + getId() + ",\n" +
					"  \"name\": \"" + getName() + "\",\n" +
					"  \"visits\": [\n" +
					"    {\n" +
					"      \"date\": \"2022-06-27\",\n" +
					"      \"description\": \"string\",\n" +
					"      \"id\": 0\n" +
					"    }\n" +
					"  ]\n" +
					"}")
			.when()
			.post("/owners/" + getOwner_id() + "/pets")
			.then()
			.statusCode(201)
			.body(containsString(getName()))
			.body("visits", hasItem(hasEntry("description", "string")));
	}


	@DisplayName("Добавить питомцев посетителю по его Id и Id питомца")
	@Test
	public void postOwnersPets() throws SQLException {
		setDown();
		given()
			.contentType("application/json")
			.body(
				"{\n" +
					"  \"birthDate\": \"" + getBirthDate().toString() + "\",\n" +
					"  \"id\": " + getId() + ",\n" +
					"  \"name\": \"" + getName() + "\",\n" +
					"  \"visits\": [\n" +
					"    {\n" +
					"      \"date\": \"2022-06-27\",\n" +
					"      \"description\": \"string\",\n" +
					"      \"id\": 0\n" +
					"    }\n" +
					"  ]\n" +
					"}")
			.when()
			.post("/owners/" + getOwner_id() + "/pets/" + getId())
			.then()
			.statusCode(201);
	}

	@Test
	@DisplayName("Негативный тест получить питомца без Id")
	public void ShouldReturnErrorWhenPathDoesntExist() {
		given()
			.contentType("application/json")
			.when()
			.get("/owners/{ownerId}/pets")
			.then()
			.statusCode(400);
	}
}

