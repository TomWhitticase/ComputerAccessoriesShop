
public class Mouse extends Product {
	// attributes
	private final int numberOfButtons;
	private final MouseType deviceType;

	// constructor
	public Mouse(String barcode, MouseType deviceType, String brand, String colour, ConnectivityType connectivity,
			int quantity, float originalCost, float retailPrice, int numberOfButtons) {
		super(barcode, "mouse", brand, colour, connectivity, quantity, originalCost, retailPrice);

		this.deviceType = deviceType;
		this.numberOfButtons = numberOfButtons;
	}

	// getters
	public int getNumberOfButtons() {
		return numberOfButtons;
	}

	public MouseType getDeviceType() {
		return deviceType;
	}

}
