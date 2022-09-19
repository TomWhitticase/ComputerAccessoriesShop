
public class Address {
	// attributes
	private String houseNumber;
	private String postcode;
	private String city;

	// constructor
	public Address(String houseNumber, String postcode, String city) {
		super();
		this.houseNumber = houseNumber;
		this.postcode = postcode;
		this.city = city;
	}

	// getters
	public String getHouseNumber() {
		return houseNumber;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getCity() {
		return city;
	}
}
