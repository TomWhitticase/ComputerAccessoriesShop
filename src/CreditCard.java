import java.util.regex.Pattern;

public class CreditCard extends Payment {
	// attributes
	private final String cardNumber;
	private final String securityCode;

	// constructor
	public CreditCard(String cardNumber, String securityCode) {
		super();
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
	}

	// validate the payment method, returns error message if invalid
	public String validate() {
		Pattern isDigitRegex = Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE);
		if (!(isDigitRegex.matcher(cardNumber).find() && cardNumber.length() == 6)) {
			return "Must enter a valid card number (6 digits)";
		}
		if (!(isDigitRegex.matcher(securityCode).find() && securityCode.length() == 3)) {
			return "Must enter a valid security code (3 digits)";
		}
		return null;
	}

}
