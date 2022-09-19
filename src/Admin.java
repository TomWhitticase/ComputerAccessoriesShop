import java.util.List;

import javax.swing.JOptionPane;

public class Admin extends User {
	// constructor
	public Admin(String id, String username, String name, String houseNumber, String postcode, String city) {
		super(id, username, name, houseNumber, postcode, city, "admin");
	}

	// get products from stock
	public List<Product> getProducts() {
		List<Product> availableProducts = Database.loadStock();
		return availableProducts;
	}

	// add product to stock
	public void addProduct(Product item) {
		if (Database.productExists(item)) {
			// if adding an existing product to stock
			Product existingProduct = Database.getExistingProductWithBarcode(item.getBarcode());
			if (!existingProduct.productIsEqual(item)) {
				JOptionPane.showMessageDialog(null,
						"Cannot add product. There is an existing product with given barcode but different attributes",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int response = JOptionPane.showConfirmDialog(null,
					"Product already in stock. increase quantity by " + item.getQuantity() + "?", "Add product",
					JOptionPane.OK_CANCEL_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				item.setQuantity(
						item.getQuantity() + Database.getExistingProductWithBarcode(item.getBarcode()).getQuantity());
				Database.setQuantity(item);
			}

		} else {
			// if adding a new product to stock
			int response = JOptionPane.showConfirmDialog(null,
					"Add new product to stock? (quantity: " + item.getQuantity() + ")", "Add product",
					JOptionPane.OK_CANCEL_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				Database.newProduct(item);
				Database.setQuantity(item);
			}
		}
	}
}
