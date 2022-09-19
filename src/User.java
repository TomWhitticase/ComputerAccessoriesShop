import java.util.List;

public abstract class User {
	// attributes
	private final String id;
	private final String username;
	private final String name;
	private final Address address;
	private final String role;

	// constructor
	public User(String id, String username, String name, String houseNumber, String postcode, String city,
			String role) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.address = new Address(houseNumber, postcode, city);
		this.role = role;
	}

	// getters
	public String getUsername() {
		return username;
	}

	public String getRole() {
		return role;
	}

	public Address getAddress() {
		return address;
	}

	public String getName() {
		return name;
	}

	public abstract List<Product> getProducts();

}
