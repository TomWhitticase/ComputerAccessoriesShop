import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Database {
	// file paths
	static final String USER_ACCOUNTS_PATH = "Resources/UserAccounts.txt";
	static final String STOCK_PATH = "Resources/Stock.txt";

	// get a list of stock Products from txt file
	static public List<Product> loadStock() {
		List<Product> stock = new ArrayList<Product>();
		File myObj = new File(STOCK_PATH);
		Scanner myReader;
		try {
			myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();

				String[] splitData = data.split(", ");
				if (splitData[1].equals("mouse")) {
					Mouse newMouse = new Mouse(splitData[0], MouseType.valueOf(splitData[2].toUpperCase()),
							splitData[3], splitData[4], ConnectivityType.valueOf(splitData[5].toUpperCase()),
							Integer.parseInt(splitData[6]), Float.parseFloat(splitData[7]),
							Float.parseFloat(splitData[8]), Integer.parseInt(splitData[9]));
					stock.add(newMouse);
				}
				if (splitData[1].equals("keyboard")) {
					Keyboard newKeyboard = new Keyboard(splitData[0], KeyboardType.valueOf(splitData[2].toUpperCase()),
							splitData[3], splitData[4], ConnectivityType.valueOf(splitData[5].toUpperCase()),
							Integer.parseInt(splitData[6]), Float.parseFloat(splitData[7]),
							Float.parseFloat(splitData[8]), LayoutType.valueOf(splitData[9].toUpperCase()));
					stock.add(newKeyboard);
				}
			}
			myReader.close();
			// sort by ascending retail price
			stock = sortProductsByPrice(stock);
			return stock;
		} catch (FileNotFoundException e) {
			System.out.println("An error occured reading stock file");
			return null;
		}
	}

	// returns the product in stock with matching given barcode
	public static Product getExistingProductWithBarcode(String barcode) {
		List<Product> stock = Database.loadStock();
		for (Product product : stock) {
			if (product.getBarcode().equals(barcode))
				return product;
		}
		return null;
	}

	// sort products by their retail price
	private static List<Product> sortProductsByPrice(List<Product> products) {
		List<Product> sorted = new ArrayList<Product>();

		sorted.add(products.get(0));
		for (int i = 1; i < products.size(); i++) {
			boolean added = false;
			for (int j = 0; j < sorted.size(); j++) {
				if (sorted.get(j).getRetailPrice() > products.get(i).getRetailPrice()) {
					sorted.add(j, products.get(i));
					added = true;
					break;
				}
			}
			if (!added)
				sorted.add(products.get(i));
		}
		return sorted;
	}

	// get a list of Users from the txt file
	public static List<User> getUserList() {
		List<User> userList = new ArrayList<User>();
		try {
			File myObj = new File(USER_ACCOUNTS_PATH);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String[] splitLine = myReader.nextLine().split(", ");
				if (splitLine[6].equals("admin")) {
					// if user is an admin
					Admin newAdmin = new Admin(splitLine[0], splitLine[1], splitLine[2], splitLine[3], splitLine[4],
							splitLine[5]);
					userList.add(newAdmin);
				} else {
					// if user is a customer
					Customer newCustomer = new Customer(splitLine[0], splitLine[1], splitLine[2], splitLine[3],
							splitLine[4], splitLine[5]);
					userList.add(newCustomer);
				}

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred reading user accounts file");
		}
		return userList;
	}

	// add a new product to stock
	public static void newProduct(Product item) {
		List<Product> products = loadStock();
		products.add(item);
		products = sortProductsByPrice(products);
		saveStock(products);
	}

	// check if a product exists with a given barcode
	public static boolean productExists(Product item) {
		List<Product> products = loadStock();
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getBarcode().equals(item.getBarcode())) {
				return true;
			}
		}
		return false;
	}

	// set quantity of item in stock
	public static void setQuantity(Product item) {
		List<Product> products = loadStock();
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getBarcode().equals(item.getBarcode())) {
				products.get(i).setQuantity(item.getQuantity());
				saveStock(products);
				return;

			}
		}
	}

	// removes a list of products from stock
	public static void removeStock(List<Product> basket) {
		List<Product> stock = loadStock();
		for (int i = 0; i < basket.size(); i++) {
			for (int j = 0; j < stock.size(); j++) {
				if (basket.get(i).getBarcode().equals(stock.get(j).getBarcode())) {
					stock.get(j).setQuantity(stock.get(j).getQuantity() - basket.get(i).getQuantity());
				}
			}
		}
		saveStock(stock);
	}

	// saves a list of given Products to the stock file
	public static void saveStock(List<Product> stock) {
		String lines = "";
		for (Product item : stock) {
			String line = "";
			line += item.getBarcode();
			line += ", " + item.getDeviceName();
			if (item.getDeviceName().equals("mouse")) {
				line += ", " + ((Mouse) item).getDeviceType().toString().toLowerCase();
			}
			if (item.getDeviceName().equals("keyboard")) {
				line += ", " + ((Keyboard) item).getDeviceType().toString().toLowerCase();
			}
			line += ", " + item.getBrand();
			line += ", " + item.getColour();
			line += ", " + item.getConnectivity().toString().toLowerCase();
			line += ", " + item.getQuantity();
			line += ", " + item.getOriginalCost();
			line += ", " + item.getRetailPrice();
			if (item.getDeviceName().equals("mouse")) {
				line += ", " + ((Mouse) item).getNumberOfButtons();
			}
			if (item.getDeviceName().equals("keyboard")) {

				line += ", " + ((Keyboard) item).getLayout().toString();
			}
			lines += line + "\n";
		}

		FileWriter myWriter;
		try {
			myWriter = new FileWriter(STOCK_PATH);
			myWriter.write(lines);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occured writing stock to file");
			e.printStackTrace();
		}

	}
}