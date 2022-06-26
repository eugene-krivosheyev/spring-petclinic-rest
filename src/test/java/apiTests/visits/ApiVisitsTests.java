package apiTests.visits;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.*;

public class ApiVisitsTests extends VisitsPojo{
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

		Statement sqlPets = connection.createStatement();
		ResultSet resultSet = sqlPets.executeQuery("SELECT * from pets OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY;");
		while (resultSet.next()) {
			setPetId(resultSet.getInt(1));
		}

		setId(random.nextInt());
		setVisitDate(date);
		setDescription(randomAlphabetic(3));

		PreparedStatement sql = connection.prepareStatement(
			"INSERT INTO visits(id, pet_id, visit_date, description) VALUES(?,?,?,?)"
		);
		sql.setInt(1, getId());
		sql.setInt(2, getPetId());
		sql.setDate(3, Date.valueOf(getVisitDate()));
		sql.setString(4, getDescription());
		sql.executeUpdate();
	}

	@AfterEach
	public void setDown() throws SQLException {
		PreparedStatement sqlVisits = connection.prepareStatement(
			" DELETE FROM visits WHERE pet_id = ?"
		);
		sqlVisits.setInt(1, getPetId());
		sqlVisits.executeUpdate();
	}

	/**
	 * Возвращает 500
	 * TO-BE
	 * Возвращает запись по Id
	 */
	@Disabled
	@DisplayName("Получить визит по Id питомца и владельца")
	@Test
	public void getVisits() {
		when()
			.get("/owners/" + getId() + "/pets/" + getPetId() + "/visits")
			.then()
			.statusCode(200)
			.body(containsString(getDescription()))
			.body("", hasItem(hasEntry("id", getId())));
	}


	/**
	 * Возвращает 500
	 * TO-BE
	 * Добавляет запись
	 */
	@Disabled
	@Test
	public void postVisits() throws SQLException{
		setDown();
		given()
			.contentType("application/json")
			.body(
				"{\n" +
					"  \"date\": \"" + getVisitDate() + "\",\n" +
					"  \"description\": \"" + getDescription() + "\",\n" +
					"  \"id\": 0\n" +
					"}")
			.when()
			.post("/owners/" + getId() + "/pets/" + getPetId())
			.then()
			.statusCode(201);
	}
}
