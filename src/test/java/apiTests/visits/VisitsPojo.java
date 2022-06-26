package apiTests.visits;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class VisitsPojo {
	@SerializedName("id")
	private Integer id;

	@SerializedName("pet_id")
	private Integer petId;

	@SerializedName("visit_date")
	private LocalDate visitDate;

	@SerializedName("description")
	private String description;

	public Integer getId() {
		return id;
	}

	public VisitsPojo setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getPetId() {
		return petId;
	}

	public VisitsPojo setPetId(Integer petId) {
		this.petId = petId;
		return this;
	}

	public LocalDate getVisitDate() {
		return visitDate;
	}

	public VisitsPojo setVisitDate(LocalDate visitDate) {
		this.visitDate = visitDate;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public VisitsPojo setDescription(String description) {
		this.description = description;
		return this;
	}
}
