package apiTests.vet;

import com.google.gson.annotations.SerializedName;

public class VetsPojo {
	@SerializedName("id")
	private Integer id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("last_name")
	private String lastName;

	public Integer getId() {
		return id;
	}

	public VetsPojo setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public VetsPojo setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public VetsPojo setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
}
