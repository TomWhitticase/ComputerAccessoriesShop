import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

	// customer shopping basket
	private List<Product> basket;

	public Customer(String id, String username, String name, String houseNumber, String postcode, String city) {
		super(id, username, name, houseNumber, postcode, city, "customer");
		// TODO Auto-generated constructor stub
		basket = new ArrayList<Product>();
	}

	// get the total cost of all items in basket
	public float getCheckoutCost() {
		float totalCost = 0;
		for (Product product : basket) {
			totalCost += product.getRetailPrice() * product.getQuantity();
		}
		return totalCost;
	}

	// only returns products that are in stock
	public List<Product> getProducts() {
		List<Product> availableProducts = Database.loadStock();

		for (int i = 0; i < availableProducts.size(); i++) {
			for (int j = 0; j < basket.size(); j++) {
				if (availableProducts.get(i).getBarcode().equals(basket.get(j).getBarcode())) {
					availableProducts.get(i)
							.setQuantity(availableProducts.get(i).getQuantity() - basket.get(j).getQuantity());
				}
			}
		}
		for (int i = 0; i < availableProducts.size(); i++) {
			if (availableProducts.get(i).getQuantity() == 0) {
				availableProducts.remove(i);
				i = 0;
			}
		}
		return availableProducts;
	}

	// adds a given product to the customers basket, if the product is already in
	// basket then increase the product's quantity
	public void addToBasket(Product product) {
		boolean newItem = true;
		for (int i = 0; i < basket.size(); i++) {
			if (basket.get(i).getBarcode().equals(product.getBarcode())) {
				basket.get(i).setQuantity(basket.get(i).getQuantity() + product.getQuantity());
				newItem = false;
				break;
			}
		}
		if (newItem) {
			basket.add(product);
		}
	}

	public List<Product> getBasket() {
		return basket;
	}

	public void clearBasket() {
		basket.clear();
	}
}
