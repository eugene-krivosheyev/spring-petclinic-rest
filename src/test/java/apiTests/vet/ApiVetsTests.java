package apiTests.vet;

import apiTests.pet.PetsPojo;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Random;

import static io.restassured.RestAssured.when;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.*;

public class ApiVetsTests extends VetsPojo{
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

		setId(random.nextInt());
		setFirstName(randomAlphabetic(3));
		setLastName(randomAlphabetic(3));

		PreparedStatement sql = connection.prepareStatement(
			"INSERT INTO vets(id, first_name, last_name) VALUES(?,?,?)"
		);
		sql.setInt(1, getId());
		sql.setString(2, getFirstName());
		sql.setString(3, getLastName());
		sql.executeUpdate();
	}

	@AfterEach
	public void setDown() throws SQLException {
		PreparedStatement sql = connection.prepareStatement(
			" DELETE FROM vets WHERE id = ?"
		);
		sql.setInt(1, getId());
		sql.executeUpdate();
	}

	/**
	 * Возвращает первые 6 строк
	 * TO-BE возвращает все записи
	 */
	@Disabled
	@DisplayName("Получить всех ветеринаров")
	@Test
	public void getVets() {
		when()
			.get("/vets" )
			.then()
			.statusCode(200)
			.body(containsString(getFirstName()))
			.body("", hasItem(hasEntry("id", getId())));
	}
}
