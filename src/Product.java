public abstract class Product {
	// attributes
	private final String barcode;
	private final String deviceName;
	private final String brand;
	private final String colour;
	private final ConnectivityType connectivity;
	private int quantity;
	private final float originalCost;
	private final float retailPrice;

	// constructor
	public Product(String barcode, String deviceName, String brand, String colour, ConnectivityType connectivity,
			int quantity, float originalCost, float retailPrice) {
		super();
		this.barcode = barcode;
		this.deviceName = deviceName;
		this.brand = brand;
		this.colour = colour;
		this.connectivity = connectivity;
		this.quantity = quantity;
		this.originalCost = originalCost;
		this.retailPrice = retailPrice;
	}

	// getters
	public String getBarcode() {
		return barcode;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getBrand() {
		return brand;
	}

	public String getColour() {
		return colour;
	}

	public ConnectivityType getConnectivity() {
		return connectivity;
	}

	public int getQuantity() {
		return quantity;
	}

	public float getOriginalCost() {
		return originalCost;
	}

	public float getRetailPrice() {
		return retailPrice;
	}

	// setters
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// method for checking if a given Products attributes match this Product
	public boolean productIsEqual(Product product) {
		if (!barcode.equals(product.getBarcode()))
			return false;
		if (!deviceName.equals(product.getDeviceName()))
			return false;
		if (!brand.equals(product.getBrand()))
			return false;
		if (!colour.equals(product.getColour()))
			return false;
		if (!connectivity.equals(product.getConnectivity()))
			return false;
		if (originalCost != product.getOriginalCost())
			return false;
		if (retailPrice != product.getRetailPrice())
			return false;
		if (!deviceName.equals(product.getDeviceName()))
			return false;
		if (deviceName.equals("mouse")) {
			if (((Mouse) this).getNumberOfButtons() != ((Mouse) product).getNumberOfButtons())
				return false;
			if (!((Mouse) this).getDeviceType().equals(((Mouse) product).getDeviceType()))
				return false;
		}
		if (deviceName.equals("keyboard")) {
			if (!((Keyboard) this).getLayout().equals(((Keyboard) product).getLayout()))
				return false;
			if (!((Keyboard) this).getDeviceType().equals(((Keyboard) product).getDeviceType()))
				return false;
		}
		return true;
	}

}
