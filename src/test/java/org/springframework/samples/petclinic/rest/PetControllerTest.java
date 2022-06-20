package org.springframework.samples.petclinic.rest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class PetControllerTest extends RestApiBaseTest {
	int petID;
	int ownerID;
	String petName = RandomStringUtils.randomAlphabetic(3);
	String ownerFName = RandomStringUtils.randomAlphabetic(7);
	String ownerLName = RandomStringUtils.randomAlphabetic(7);
	String ownerAddress = RandomStringUtils.randomAlphabetic(10);
	String ownersPhone = "" + RandomUtils.nextInt(600000000, 699999999);

	@BeforeEach
	public void prepareTestData() throws SQLException {
		Statement maxOwnerID = connection.createStatement();
		ResultSet maxOwnerResult = maxOwnerID.executeQuery("select max(ID) from owners");
		maxOwnerResult.next();
		ownerID = maxOwnerResult.getInt(1) + 1;

		Statement maxPetID = connection.createStatement();
		ResultSet maxPetResult = maxPetID.executeQuery("select max(ID) from pets");
		maxPetResult.next();
		petID = maxPetResult.getInt(1) + 1;

		PreparedStatement owner = connection.prepareStatement(
			"INSERT INTO OWNERS(id, first_name, last_name, address, city,telephone) VALUES(?,?,?,?,?,?)",
			Statement.RETURN_GENERATED_KEYS);
		owner.setInt(1, ownerID);
		owner.setString(2, ownerFName);
		owner.setString(3, ownerLName);
		owner.setString(4, ownerAddress);
		owner.setString(5, "TO");
		owner.setString(6, ownersPhone);
		owner.executeUpdate();

		PreparedStatement pet = connection.prepareStatement(
			"INSERT INTO pets(id, name, owner_id, birth_date) VALUES(?,?,?, current_date)",
			Statement.RETURN_GENERATED_KEYS);
		pet.setInt(1, petID);
		pet.setString(2, petName);
		pet.setInt(3, ownerID);
		pet.executeUpdate();
	}

	@AfterEach
	public void clearTestsData() throws SQLException {
		Statement checkPets = connection.createStatement();
		ResultSet petsResult = checkPets.executeQuery("SELECT * FROM pets where id > 13");
		if (petsResult.next()) {
			PreparedStatement delete = connection.prepareStatement("DELETE FROM pets WHERE ID >13");
			delete.executeUpdate();
		}

		Statement checkOwners = connection.createStatement();
		ResultSet ownersResult = checkOwners.executeQuery("SELECT * FROM owners where id > 10");
		if (ownersResult.next()) {
			PreparedStatement delete = connection.prepareStatement("DELETE FROM owners WHERE ID >10");
			delete.executeUpdate();
		}
	}


	@Test
	public void getOwnerPetByID() {
		when()
			.get("owners/" + ownerID + "/pets")
			.then()
			.statusCode(200)
			.body("[0].id", is(petID),
				"[0].name", is(petName),
				"[0].new", is(false)
			);
	}

	@Test
	public void postNewPetAndGetID() {
		given()
			.contentType("application/json")
			.body("{\n" +
				"  \"birthDate\": \"2022-06-20\",\n" +
				"  \"name\": \""+ RandomStringUtils.randomAlphabetic(3) +"\"\n" +
				"}")
			.when()
			.post("owners/" + ownerID + "/pets")
			.then()
			.statusCode(201)
			.body("id", not(empty()));
	}
}
