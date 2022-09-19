import java.util.regex.Pattern;

public class PayPal extends Payment {
	// attributes
	private final String email;

	// constructor
	public PayPal(String email) {
		super();
		this.email = email;
	}

	// validate the payment method, returns error message if invalid
	public String validate() {
		Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		if (emailRegex.matcher(email).find()) {
			return null;
		} else {
			return "Must enter a valid email address";
		}
	}
}
