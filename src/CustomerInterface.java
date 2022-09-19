import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CustomerInterface extends JFrame {
	private static final long serialVersionUID = 1L;
	private final Customer user;
	private JPanel contentPane;
	private JTable table;
	private JTable basketTable;
	private JScrollPane scrollPane;
	private JScrollPane basketScrollPane;
	private JTextField searchBrandTxt;
	private JComboBox<Object> filterDeviceCmbx;
	private JTextField filterNumberOfButtonsTxt;
	private JComboBox<Object> filterLayoutCmbx;
	private DefaultTableModel stockTableModel;
	private DefaultTableModel basketTableModel;
	private JComboBox<Object> selectPaymentMethodCmbx;
	private JLabel emailAddressLbl;
	private JTextField emailAddressTxt;
	private JLabel cardNumberLbl;
	private JTextField cardNumberTxt;
	private JTextField securityCodeTxt;
	private JLabel securityCodeLbl;
	private JLabel totalCostLbl;

	public CustomerInterface(Customer user) {
		this.user = user;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(CustomerInterface.class.getResource("/images/mouse.png")));
		setTitle("Computer Accessories Shop (Customer)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		// user name
		JLabel usernameLbl = new JLabel("User: " + user.getName());
		usernameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		usernameLbl.setBounds(769, 10, 287, 42);
		contentPane.add(usernameLbl);

		JButton logoutBtn = new JButton("Logout");
		contentPane.setLayout(null);
		logoutBtn.setBounds(1074, 20, 102, 31);
		// logout button action
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Login frame = new Login();
				frame.setVisible(true);
				dispose();
			}
		});
		contentPane.add(logoutBtn);
		table = new JTable();
		stockTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			// do not allow cells to be editted
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setModel(stockTableModel);
		basketTable = new JTable();
		basketTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			// do not allow cells to be editted
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		basketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		basketTable.getTableHeader().setReorderingAllowed(false);
		basketTable.getTableHeader().setResizingAllowed(false);
		basketTable.setModel(basketTableModel);
		scrollPane = new JScrollPane(table);
		basketScrollPane = new JScrollPane(basketTable);
		scrollPane.setBounds(10, 72, 1166, 186);
		basketScrollPane.setBounds(10, 380, 1166, 173);
		contentPane.add(scrollPane);
		contentPane.add(basketScrollPane);
		JButton addToBasketBtn = new JButton("Add to basket");
		// add to basket action
		addToBasketBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// add selected item from table to basket
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "No item selected", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				String selectedProductBarcode = table.getModel().getValueAt(row, 0).toString();
				Product selectedProduct = Database.getExistingProductWithBarcode(selectedProductBarcode);
				int maxQuantity = selectedProduct.getQuantity();
				int inputQuantity = maxQuantity + 1;
				while (inputQuantity > maxQuantity || inputQuantity < 1) {
					// get quantity to add from user input
					String input = JOptionPane.showInputDialog("Enter quantity (Max " + maxQuantity + ")", 1);
					if (input == null) {// if cancel pressed then stop
						return;
					}
					try {// check if quantity input is a valid
						inputQuantity = Integer.parseInt(input);
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null, "Please enter an integer", "Error",
								JOptionPane.ERROR_MESSAGE);
						continue;
					}
					if (inputQuantity > maxQuantity) {
						JOptionPane.showMessageDialog(null, "Invalid quantity. Not enough items in stock", "Error",
								JOptionPane.ERROR_MESSAGE);
						continue;
					}
					if (inputQuantity < 1) {
						JOptionPane.showMessageDialog(null, "Invalid quantity. Must be greater than 0", "Error",
								JOptionPane.ERROR_MESSAGE);
						continue;
					}
				}
				// add to basket and update tables
				selectedProduct.setQuantity(inputQuantity);
				user.addToBasket(selectedProduct);
				updateBasketTable();
				updateStockTable();

			}
		});
		addToBasketBtn.setBounds(20, 280, 177, 36);
		contentPane.add(addToBasketBtn);

		JLabel basketLbl = new JLabel("Basket");
		basketLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		basketLbl.setBounds(10, 351, 153, 19);
		contentPane.add(basketLbl);

		JButton emptyBasketBtn = new JButton("Empty basket");
		emptyBasketBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.clearBasket();
				updateStockTable();
				updateBasketTable();
			}
		});
		emptyBasketBtn.setBounds(227, 280, 120, 36);
		contentPane.add(emptyBasketBtn);

		String[] keyboardLayouts = { "All", "US", "UK" };
		filterLayoutCmbx = new JComboBox<Object>(keyboardLayouts);
		filterLayoutCmbx.setEnabled(false);
		// filter keyboard layout combobox change item
		filterLayoutCmbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateStockTable();
			}
		});
		filterLayoutCmbx.setBounds(445, 37, 75, 19);
		contentPane.add(filterLayoutCmbx);

		JLabel searchLbl = new JLabel("Search");
		searchLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		searchLbl.setBounds(10, 16, 94, 31);
		contentPane.add(searchLbl);

		JLabel filterDeviceLbl = new JLabel("Filter by device");
		filterDeviceLbl.setBounds(314, 9, 94, 19);
		contentPane.add(filterDeviceLbl);

		JLabel filterLayoutLbl = new JLabel("Filter by layout");
		filterLayoutLbl.setEnabled(false);
		filterLayoutLbl.setBounds(445, 14, 96, 13);
		contentPane.add(filterLayoutLbl);

		searchBrandTxt = new JTextField();
		// typing in search brand text field
		searchBrandTxt.addCaretListener(e -> {
			updateStockTable();
		});

		searchBrandTxt.setBounds(136, 35, 139, 19);
		contentPane.add(searchBrandTxt);
		searchBrandTxt.setColumns(10);

		JLabel searchByBrandLbl = new JLabel("Search by brand");
		searchByBrandLbl.setBounds(136, 12, 142, 13);
		contentPane.add(searchByBrandLbl);

		JLabel filterNumberOfButtonsLbl = new JLabel("Filter by number of buttons");
		filterNumberOfButtonsLbl.setEnabled(false);
		filterNumberOfButtonsLbl.setBounds(568, 14, 182, 13);

		contentPane.add(filterNumberOfButtonsLbl);

		filterNumberOfButtonsTxt = new JTextField();
		filterNumberOfButtonsTxt.setEnabled(false);
		filterNumberOfButtonsTxt.setBounds(568, 37, 25, 19);
		// typing in number of buttons text field
		filterNumberOfButtonsTxt.addCaretListener(e -> {
			updateStockTable();

		});
		contentPane.add(filterNumberOfButtonsTxt);
		filterNumberOfButtonsTxt.setColumns(10);

		String[] productTypes = { "All", "Mouse", "Keyboard" };
		filterDeviceCmbx = new JComboBox<Object>(productTypes);
		// value changed in device combobox
		filterDeviceCmbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filterDeviceCmbx.getSelectedItem().equals("Mouse")) {
					// enable mouse filter options and disable keyboard filter options
					filterNumberOfButtonsTxt.setEnabled(true);
					filterNumberOfButtonsLbl.setEnabled(true);
					filterLayoutCmbx.setEnabled(false);
					filterLayoutLbl.setEnabled(false);
				} else if (filterDeviceCmbx.getSelectedItem().equals("Keyboard")) {
					// enable keyboard filter options and disable mouse filter options
					filterLayoutCmbx.setEnabled(true);
					filterLayoutLbl.setEnabled(true);

					filterNumberOfButtonsTxt.setEnabled(false);
					filterNumberOfButtonsLbl.setEnabled(false);
				} else {
					filterLayoutCmbx.setEnabled(false);
					filterLayoutLbl.setEnabled(false);

					filterNumberOfButtonsTxt.setEnabled(false);
					filterNumberOfButtonsLbl.setEnabled(false);
				}

				updateStockTable();
			}
		});
		filterDeviceCmbx.setBounds(314, 35, 84, 19);
		contentPane.add(filterDeviceCmbx);

		totalCostLbl = new JLabel("Total Cost £0.00");
		totalCostLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		totalCostLbl.setHorizontalAlignment(SwingConstants.CENTER);
		totalCostLbl.setBounds(938, 270, 238, 36);
		contentPane.add(totalCostLbl);

		String[] paymentMethods = { "PayPal", "Credit Card" };

		selectPaymentMethodCmbx = new JComboBox<Object>(paymentMethods);
		selectPaymentMethodCmbx.setSelectedIndex(-1);
		// payment method combobox value changed
		selectPaymentMethodCmbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectPaymentMethodCmbx.getSelectedItem().equals("PayPal")) {
					// enable paypal inputs and disable credit card gui inputs
					emailAddressTxt.setEnabled(true);
					emailAddressLbl.setEnabled(true);
					cardNumberLbl.setEnabled(false);
					cardNumberTxt.setEnabled(false);
					securityCodeLbl.setEnabled(false);
					securityCodeTxt.setEnabled(false);
				} else {
					// enable credit card inputs and disable paypal gui inputs
					emailAddressTxt.setEnabled(false);
					emailAddressLbl.setEnabled(false);
					cardNumberLbl.setEnabled(true);
					cardNumberTxt.setEnabled(true);
					securityCodeLbl.setEnabled(true);
					securityCodeTxt.setEnabled(true);
				}
			}
		});
		selectPaymentMethodCmbx.setBounds(527, 273, 171, 21);
		contentPane.add(selectPaymentMethodCmbx);

		JLabel selectPaymentMethodLbl = new JLabel("Select Payment Method");
		selectPaymentMethodLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		selectPaymentMethodLbl.setBounds(357, 273, 160, 21);
		contentPane.add(selectPaymentMethodLbl);

		emailAddressLbl = new JLabel("Email Address");
		emailAddressLbl.setEnabled(false);
		emailAddressLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		emailAddressLbl.setBounds(375, 318, 110, 13);
		contentPane.add(emailAddressLbl);

		emailAddressTxt = new JTextField();
		emailAddressTxt.setEnabled(false);
		emailAddressTxt.setBounds(495, 315, 120, 19);
		contentPane.add(emailAddressTxt);
		emailAddressTxt.setColumns(10);

		cardNumberLbl = new JLabel("Card Number");
		cardNumberLbl.setEnabled(false);
		cardNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		cardNumberLbl.setBounds(644, 318, 84, 13);
		contentPane.add(cardNumberLbl);

		cardNumberTxt = new JTextField();
		cardNumberTxt.setEnabled(false);
		cardNumberTxt.setBounds(738, 315, 55, 19);
		contentPane.add(cardNumberTxt);
		cardNumberTxt.setColumns(10);

		securityCodeTxt = new JTextField();
		securityCodeTxt.setEnabled(false);
		securityCodeTxt.setBounds(738, 338, 32, 19);
		contentPane.add(securityCodeTxt);
		securityCodeTxt.setColumns(10);

		securityCodeLbl = new JLabel("Security Code");
		securityCodeLbl.setEnabled(false);
		securityCodeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		securityCodeLbl.setBounds(629, 341, 102, 13);
		contentPane.add(securityCodeLbl);

		JButton payBtn = new JButton("Pay");
		// pay button pressed
		payBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if basket is epty show an error message and stop
				if (user.getBasket().size() == 0) {
					JOptionPane.showMessageDialog(null, "Basket is empty", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// if a payment method has not been selected show an error message and stop
				if (selectPaymentMethodCmbx.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Must select a payment method", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				// paying by payal
				if (selectPaymentMethodCmbx.getSelectedItem().equals("PayPal")) {
					PayPal payment = new PayPal(emailAddressTxt.getText());
					// validate payment method, if invalid stop
					if (payment.validate() != null) {
						JOptionPane.showMessageDialog(null, payment.validate(), "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					checkout("PayPal");
				} else { // paying by credit card
					CreditCard payment = new CreditCard(cardNumberTxt.getText(), securityCodeTxt.getText());
					// validate payment method, if invalid stop.
					if (payment.validate() != null) {
						JOptionPane.showMessageDialog(null, payment.validate(), "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					checkout("Credit Card");
				}
			}
		});
		payBtn.setBounds(1010, 316, 110, 43);
		contentPane.add(payBtn);

		// update stock and basket tables
		updateStockTable();
		updateBasketTable();
	}

	// paying for items in basket and removing them from stock
	public void checkout(String paymentMethod) {
		String totalCostStr = NumberFormat.getCurrencyInstance().format(user.getCheckoutCost());
		Address address = user.getAddress();
		// remove contents of basket from stock
		Database.removeStock(user.getBasket());
		user.clearBasket();
		updateStockTable();
		updateBasketTable();
		// display payment message
		JOptionPane.showMessageDialog(null,
				"Amount paid using " + paymentMethod + " is " + totalCostStr + " and the delivery address is "
						+ address.getHouseNumber() + ", " + address.getCity() + ", " + address.getPostcode(),
				"Payment", JOptionPane.INFORMATION_MESSAGE);
	}

	// update the stock table being displayed
	public void updateStockTable() {
		String searchBrandText = searchBrandTxt.getText();

		String[] columnNames = { "BARCODE", "DEVICE NAME", "DEVICE TYPE", "BRAND", "COLOUR", "CONNECTIVITY",
				"QUANTITY IN STOCK", "RETAIL PRICE", "ADDITIONAL INFO" };

		List<Product> stockList = user.getProducts();

		// if searching for a brand remove products that do not math search;
		stockList.removeIf(product -> (!product.getBrand().toLowerCase().contains(searchBrandText.toLowerCase())));

		// if filtering products remove the other products
		if (filterDeviceCmbx.getSelectedItem().equals("Mouse")) {
			stockList.removeIf(product -> (!product.getDeviceName().equals("mouse")));
			if (filterNumberOfButtonsTxt.getText().length() != 0) {
				stockList.removeIf(product -> (!Integer.toString(((Mouse) product).getNumberOfButtons())
						.equals(filterNumberOfButtonsTxt.getText())));

			}
		}
		if (filterDeviceCmbx.getSelectedItem().equals("Keyboard")) {
			stockList.removeIf(product -> (!product.getDeviceName().equals("keyboard")));
			if (filterLayoutCmbx.getSelectedIndex() != 0) {

				stockList.removeIf(product -> (!((Keyboard) product).getLayout().toString()
						.equals(filterLayoutCmbx.getSelectedItem())));

			}
		}

		Object[][] data = new Object[stockList.size()][9];
		for (int i = 0; i < stockList.size(); i++) {
			Product item = stockList.get(i);

			data[i][0] = item.getBarcode();
			data[i][1] = item.getDeviceName();
			if (item.getDeviceName().equals("mouse")) {
				data[i][2] = ((Mouse) item).getDeviceType();
			}
			if (item.getDeviceName().equals("keyboard")) {
				data[i][2] = ((Keyboard) item).getDeviceType();
			}
			data[i][3] = item.getBrand();
			data[i][4] = item.getColour();
			data[i][5] = item.getConnectivity();
			data[i][6] = item.getQuantity();

			data[i][7] = item.getRetailPrice();
			if (item.getDeviceName().equals("mouse")) {
				data[i][8] = ((Mouse) item).getNumberOfButtons() + " (# of buttons)";
			}
			if (item.getDeviceName().equals("keyboard")) {
				data[i][8] = ((Keyboard) item).getLayout() + " (layout)";
			}
		}
		stockTableModel.setDataVector(data, columnNames);

	}

	// update the basket table being displayed
	public void updateBasketTable() {
		totalCostLbl.setText("Total Cost " + NumberFormat.getCurrencyInstance().format(user.getCheckoutCost()));

		String[] columnNames = { "BARCODE", "DEVICE NAME", "DEVICE TYPE", "BRAND", "COLOUR", "CONNECTIVITY", "QUANTITY",
				"RETAIL PRICE", "ADDITIONAL INFO" };

		List<Product> basket = user.getBasket();
		Object[][] data = new Object[basket.size()][9];
		for (int i = 0; i < basket.size(); i++) {
			Product item = basket.get(i);
			data[i][0] = item.getBarcode();
			data[i][1] = item.getDeviceName();
			if (item.getDeviceName().equals("mouse")) {
				data[i][2] = ((Mouse) item).getDeviceType();
			}
			if (item.getDeviceName().equals("keyboard")) {
				data[i][2] = ((Keyboard) item).getDeviceType();
			}
			data[i][3] = item.getBrand();
			data[i][4] = item.getColour();
			data[i][5] = item.getConnectivity();
			data[i][6] = item.getQuantity();

			data[i][7] = item.getRetailPrice() + " x " + item.getQuantity();
			if (item.getDeviceName().equals("mouse")) {
				data[i][8] = ((Mouse) item).getNumberOfButtons() + " (# of buttons)";
			}
			if (item.getDeviceName().equals("keyboard")) {
				data[i][8] = ((Keyboard) item).getLayout() + " (layout)";
			}
		}
		basketTableModel.setDataVector(data, columnNames);

	}
}
