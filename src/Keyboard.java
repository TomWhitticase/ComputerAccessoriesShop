
public class Keyboard extends Product {
	// attributes
	private final LayoutType layout;
	private final KeyboardType deviceType;

	// constructor
	public Keyboard(String barcode, KeyboardType deviceType, String brand, String colour, ConnectivityType connectivity,
			int quantity, float originalCost, float retailPrice, LayoutType layout) {
		super(barcode, "keyboard", brand, colour, connectivity, quantity, originalCost, retailPrice);

		this.layout = layout;
		this.deviceType = deviceType;
	}

	// getters
	public LayoutType getLayout() {
		return layout;
	}

	public KeyboardType getDeviceType() {
		return deviceType;
	}

}
