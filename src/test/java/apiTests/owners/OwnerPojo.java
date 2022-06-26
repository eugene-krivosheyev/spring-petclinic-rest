/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package apiTests.owners;

import com.google.gson.annotations.SerializedName;

public class OwnerPojo {

	@SerializedName("id")
	private Integer id;

	@SerializedName("address")
	private String address;

	@SerializedName("city")
	private String city;

	@SerializedName("telephone")
	private String telephone;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("fistName")
	private String firstName;

	public String getAddress() {
		return address;
	}

	public OwnerPojo setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getCity() {
		return city;
	}

	public OwnerPojo setCity(String city) {
		this.city = city;
		return this;
	}

	public String getTelephone() {
		return String.valueOf(telephone);
	}

	public OwnerPojo setTelephone(String telephone) {
		this.telephone = telephone;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public OwnerPojo setLast_name(String last_name) {
		this.lastName = last_name;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public OwnerPojo setFist_name(String fist_name) {
		this.firstName = fist_name;
		return this;
	}

	public int getId() {
		return id;
	}

	public OwnerPojo setId(int id) {
		this.id = id;
		return this;
	}
}
