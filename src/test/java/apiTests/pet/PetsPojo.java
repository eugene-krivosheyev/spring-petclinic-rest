package apiTests.pet;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class PetsPojo {
	@SerializedName("id")
	private Integer id;

	@SerializedName("name")
	private String name;

	@SerializedName("birth_date")
	private LocalDate birthDate;

	public Integer getOwner_id() {
		return owner_id;
	}

	public PetsPojo setOwner_id(Integer owner_id) {
		this.owner_id = owner_id;
		return this;
	}

	@SerializedName("owner_id")
	private Integer owner_id;

	public Integer getId() {
		return id;
	}

	public PetsPojo setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public PetsPojo setName(String name) {
		this.name = name;
		return this;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public PetsPojo setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
		return this;
	}
}
