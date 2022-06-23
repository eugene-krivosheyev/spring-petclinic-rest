package org.springframework.samples.petclinic.rest;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class OwnerControllerTest extends RestApiBaseTest {

	ArrayList<Long> ownerIds = new ArrayList<>();

	@AfterEach
	void tearDown() {
		ownerIds.forEach(x -> {
			try {
				deleteOwnerById(x);
			}
			catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		});
	}

	@Test
	void processFindFormTest() throws SQLException {
		given().spec(requestSpec).when().get("/owners").then().assertThat().statusCode(200);
	}

	@Test
	void showOwnerTest() throws SQLException {
		var firstName = RandomStringUtils.randomAlphabetic(7);
		var lastName = RandomStringUtils.randomAlphabetic(7);
		var address = RandomStringUtils.randomAlphabetic(20);
		var city = RandomStringUtils.randomAlphabetic(7);
		var telephone = RandomStringUtils.randomNumeric(10);

		var ownerId = addOwner(firstName, lastName, address, city, telephone);
		ownerIds.add(ownerId);
		given().spec(requestSpec).when().get("/owners/" + ownerId).then().assertThat().statusCode(200)
				.body("firstName", is(firstName)).body("lastName", is(lastName)).body("address", is(address))
				.body("city", is(city)).body("telephone", is(telephone));
	}

	@Test
	void processCreationFormTest() throws SQLException {
		var firstName = RandomStringUtils.randomAlphabetic(7);
		var lastName = RandomStringUtils.randomAlphabetic(7);
		var address = RandomStringUtils.randomAlphabetic(20);
		var city = RandomStringUtils.randomAlphabetic(7);
		var telephone = RandomStringUtils.randomNumeric(10);
		given().spec(requestSpec).contentType("application/json")
				.body("{" + "\"firstName\": \"" + firstName + "\", " + "\"lastName\": \"" + lastName + "\", "
						+ "\"address\": \"" + address + "\"," + "\"city\": \"" + city + "\"," + "\"telephone\": \""
						+ telephone + "\"," + "\"id\": 0," + "\"pets\": []" + "}")
				.when().post("/owners").then().assertThat().statusCode(201).body("firstName", is(firstName))
				.body("lastName", is(lastName)).body("address", is(address)).body("city", is(city))
				.body("telephone", is(telephone));

	}

	@Test
	void processUpdateOwnerFormTest() throws SQLException {
		var firstName = RandomStringUtils.randomAlphabetic(7);
		var lastName = RandomStringUtils.randomAlphabetic(7);
		var address = RandomStringUtils.randomAlphabetic(20);
		var city = RandomStringUtils.randomAlphabetic(7);
		var telephone = RandomStringUtils.randomNumeric(10);
		var ownerId = addOwner(RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(7),
				RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(7),
				RandomStringUtils.randomAlphabetic(7));
		ownerIds.add(ownerId);
		given().spec(requestSpec).contentType("application/json")
				.body("{" + "\"firstName\": \"" + firstName + "\", " + "\"lastName\": \"" + lastName + "\", "
						+ "\"address\": \"" + address + "\", " + "\"city\": \"" + city + "\", " + "\"telephone\": \""
						+ telephone + "\", " + "\"id\": 0, " + "\"pets\": [] " + "}")
				.when().log().all().post("/owners/" + ownerId).then().assertThat().statusCode(201)
				.body("firstName", is(firstName)).body("lastName", is(lastName)).body("address", is(address))
				.body("city", is(city)).body("telephone", is(telephone));

		Assertions.assertEquals(firstName, getFirstNameById(ownerId));
	}

	private Long addOwner(String firstName, String lastName, String address, String city, String telephone)
			throws SQLException {
		var statement = connection.createStatement();
		var resultSet = statement.executeQuery("(SELECT MAX(id)+1 FROM owners)");
		Long id = null;
		while (resultSet.next()) {
			id = resultSet.getLong(1);
		}
		statement.executeUpdate(String.format(
				"INSERT INTO owners (id, first_name, last_name, address, city, telephone) "
						+ "VALUES (%d, '%s', '%s', '%s', '%s', '%s')",
				id, firstName, lastName, address, city, telephone));
		return id;
	}

	private void deleteOwnerById(Long id) throws SQLException {
		var statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM owners WHERE id = " + id);
	}

	private String getFirstNameById(Long id) throws SQLException {
		var statement = connection.createStatement();
		var resultSet = statement.executeQuery("SELECT first_name FROM owners WHERE id = " + id);
		String firstName = null;
		while (resultSet.next()) {
			firstName = resultSet.getString(1);
		}
		return firstName;
	}

}
