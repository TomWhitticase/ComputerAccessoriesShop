import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class AdminInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	private final Admin user;
	private JPanel contentPane;
	private JTextField barcodeTxt;
	private JTextField brandTxt;
	private JTextField colourTxt;
	private JTextField quantityTxt;
	private JTextField originalCostTxt;
	private JTextField retailPriceTxt;
	private JLabel quantityLbl;
	private JLabel connectivityLbl;
	private JLabel colourLbl;
	private JLabel brandLbl;
	private JLabel barcodeLbl;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField numberOfButtonsTxt;
	private JComboBox<Object> keyboardTypeCmbx;
	private JComboBox<Object> keyboardLayoutCmbx;
	private JComboBox<Object> mouseTypeCmbx;
	private JComboBox<Object> connectivityCmbx;
	private JLabel keyboardTypeLbl;
	private JLabel keyboardLayoutLbl;
	private JLabel mouseTypeLbl;
	private JLabel numberOfButtonsLbl;
	private DefaultTableModel stockTableModel;

	public AdminInterface(Admin user) {
		this.user = user;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminInterface.class.getResource("/images/mouse.png")));
		setTitle("Computer Accessories Shop (Admin)");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel usernameLbl = new JLabel("User: " + user.getName());
		usernameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		usernameLbl.setBounds(761, 18, 292, 20);
		contentPane.add(usernameLbl);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 340, 1166, 213);
		contentPane.add(panel);
		panel.setLayout(null);

		barcodeLbl = new JLabel("Barcode");
		barcodeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		barcodeLbl.setBounds(32, 24, 79, 13);
		panel.add(barcodeLbl);

		barcodeTxt = new JTextField();
		barcodeTxt.setBounds(121, 21, 51, 19);
		panel.add(barcodeTxt);
		barcodeTxt.setColumns(10);

		brandLbl = new JLabel("Brand");
		brandLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		brandLbl.setBounds(32, 53, 79, 13);
		panel.add(brandLbl);

		brandTxt = new JTextField();
		brandTxt.setBounds(121, 50, 119, 19);
		panel.add(brandTxt);
		brandTxt.setColumns(10);

		colourLbl = new JLabel("Colour");
		colourLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		colourLbl.setBounds(24, 82, 87, 13);
		panel.add(colourLbl);

		colourTxt = new JTextField();
		colourTxt.setBounds(121, 79, 119, 19);
		panel.add(colourTxt);
		colourTxt.setColumns(10);

		connectivityLbl = new JLabel("Connectivity");
		connectivityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		connectivityLbl.setBounds(32, 118, 79, 13);
		panel.add(connectivityLbl);

		quantityLbl = new JLabel("Quantity");
		quantityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		quantityLbl.setBounds(599, 24, 127, 13);
		panel.add(quantityLbl);

		quantityTxt = new JTextField();
		quantityTxt.setBounds(736, 21, 41, 19);
		panel.add(quantityTxt);
		quantityTxt.setColumns(10);

		JLabel originalCostLbl = new JLabel("Original Cost");
		originalCostLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		originalCostLbl.setBounds(24, 148, 92, 13);
		panel.add(originalCostLbl);

		originalCostTxt = new JTextField();
		originalCostTxt.setBounds(121, 145, 41, 19);
		panel.add(originalCostTxt);
		originalCostTxt.setColumns(10);

		JLabel retailPriceLbl = new JLabel("Retail Price");
		retailPriceLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		retailPriceLbl.setBounds(162, 148, 87, 13);
		panel.add(retailPriceLbl);

		retailPriceTxt = new JTextField();
		retailPriceTxt.setBounds(259, 145, 41, 19);
		panel.add(retailPriceTxt);
		retailPriceTxt.setColumns(10);

		JLabel productTypeLbl = new JLabel("Product Type");
		productTypeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		productTypeLbl.setBounds(310, 24, 150, 13);
		panel.add(productTypeLbl);

		String[] productTypes = { "Mouse", "Keyboard" };
		JComboBox<Object> productTypeCmbx = new JComboBox<Object>(productTypes);
		productTypeCmbx.setBounds(470, 21, 119, 19);
		productTypeCmbx.setSelectedIndex(-1);
		panel.add(productTypeCmbx);

		// when product type is changed enable/disable required device specific GUI
		// elements
		productTypeCmbx.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (productTypeCmbx.getSelectedIndex() == 0) {
					mouseTypeLbl.setEnabled(true);
					numberOfButtonsLbl.setEnabled(true);
					mouseTypeCmbx.setEnabled(true);
					numberOfButtonsTxt.setEnabled(true);

					keyboardTypeLbl.setEnabled(false);
					keyboardTypeCmbx.setEnabled(false);
					keyboardLayoutLbl.setEnabled(false);
					keyboardLayoutCmbx.setEnabled(false);
				}
				if (productTypeCmbx.getSelectedIndex() == 1) {
					mouseTypeLbl.setEnabled(false);
					numberOfButtonsLbl.setEnabled(false);
					mouseTypeCmbx.setEnabled(false);
					numberOfButtonsTxt.setEnabled(false);

					keyboardTypeLbl.setEnabled(true);
					keyboardTypeCmbx.setEnabled(true);
					keyboardLayoutLbl.setEnabled(true);
					keyboardLayoutCmbx.setEnabled(true);
				}

			}
		});

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
		// when row selected put row values into the add item input fields
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				int i = table.getSelectedRow();
				if (i == -1)
					return;
				barcodeTxt.setText(table.getValueAt(i, 0).toString());
				brandTxt.setText(table.getValueAt(i, 3).toString());
				colourTxt.setText(table.getValueAt(i, 4).toString());
				if (table.getValueAt(i, 5).toString().equals("WIRED")) {
					connectivityCmbx.setSelectedIndex(0);
				} else {
					connectivityCmbx.setSelectedIndex(1);
				}
				quantityTxt.setText("1");
				originalCostTxt.setText(table.getValueAt(i, 7).toString());
				retailPriceTxt.setText(table.getValueAt(i, 8).toString());
				if (table.getValueAt(i, 1).toString().equals("mouse")) {
					productTypeCmbx.setSelectedIndex(0);
					mouseTypeCmbx.setEnabled(true);
					numberOfButtonsTxt.setEnabled(true);
					keyboardTypeCmbx.setEnabled(false);
					keyboardLayoutCmbx.setEnabled(false);
					if (table.getValueAt(i, 2).toString().equals("STANDARD")) {
						mouseTypeCmbx.setSelectedIndex(0);
					} else {
						mouseTypeCmbx.setSelectedIndex(1);
					}
					numberOfButtonsTxt.setText(table.getValueAt(i, 9).toString().substring(0,
							table.getValueAt(i, 9).toString().length() - 15));

				} else {
					productTypeCmbx.setSelectedIndex(1);
					mouseTypeCmbx.setEnabled(false);
					numberOfButtonsTxt.setEnabled(false);
					keyboardTypeCmbx.setEnabled(true);
					keyboardLayoutCmbx.setEnabled(true);
					if (table.getValueAt(i, 2).toString().equals("STANDARD")) {
						keyboardTypeCmbx.setSelectedIndex(0);
					} else if (table.getValueAt(i, 2).toString().equals("GAMING")) {
						keyboardTypeCmbx.setSelectedIndex(1);
					} else {
						keyboardTypeCmbx.setSelectedIndex(2);
					}
					if (table.getValueAt(i, 9).toString().substring(0, table.getValueAt(i, 9).toString().length() - 9)
							.equals("UK")) {
						keyboardLayoutCmbx.setSelectedIndex(0);
					} else {
						keyboardLayoutCmbx.setSelectedIndex(1);
					}
				}

			}
		});

		JButton addProductBtn = new JButton("Add Product");
		addProductBtn.setBounds(682, 72, 119, 33);
		addProductBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (productTypeCmbx.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Must select a product type (Mouse or Keyboard)", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				String barcode = barcodeTxt.getText();
				// check barcode input is valid
				if (barcode.length() != 6 || !barcode.matches("[0-9]+")) {
					JOptionPane.showMessageDialog(null, "Barcode must be 6 digits", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String brand = brandTxt.getText();
				// check if brand input is valid
				if (brand.length() == 0) {
					JOptionPane.showMessageDialog(null, "Must enter a brand", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String colour = colourTxt.getText();
				// check if colour input is valid
				if (colour.length() == 0) {
					JOptionPane.showMessageDialog(null, "Must enter a colour", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// check if connectivity input is valid
				ConnectivityType connectivity;
				try {
					connectivity = ConnectivityType
							.valueOf(connectivityCmbx.getSelectedItem().toString().toUpperCase());
				} catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(null, "Must enter a valid connectivity type", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				int quantity;
				// check if quantity input is valid
				try {
					quantity = Integer.parseInt(quantityTxt.getText());
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Quantity must be an integer", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				// do not allow negative quantity
				if (quantity < 1) {
					JOptionPane.showMessageDialog(null, "Quantity must be greater than 0", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				float originalCost;
				// check if original cost input is valid
				try {
					originalCost = Float.parseFloat(originalCostTxt.getText());
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Original cost must be a price", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				float retailPrice;
				// check if retail price input is valid
				try {
					retailPrice = Float.parseFloat(retailPriceTxt.getText());
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Retail price must be a price", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (productTypeCmbx.getSelectedItem().equals("Mouse")) {
					MouseType mouseType;
					// check if mouse type input is valid
					try {
						mouseType = MouseType.valueOf(mouseTypeCmbx.getSelectedItem().toString().toUpperCase());
					} catch (IllegalArgumentException e1) {
						JOptionPane.showMessageDialog(null, "Must enter a valid mouse type", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					int numberOfButtons;
					// check if number of buttons input is valid
					try {
						numberOfButtons = Integer.parseInt(numberOfButtonsTxt.getText());
						if (numberOfButtons < 0) {
							JOptionPane.showMessageDialog(null, "Number of buttons must be a positive integer", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null, "Number of buttons must be an integer", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					Mouse newItem = new Mouse(barcode, mouseType, brand, colour, connectivity, quantity, originalCost,
							retailPrice, numberOfButtons);

					user.addProduct(newItem);

				}
				if (productTypeCmbx.getSelectedItem().equals("Keyboard")) {

					KeyboardType keyboardType;
					// check if keyboard type input is valid
					try {
						keyboardType = KeyboardType
								.valueOf(keyboardTypeCmbx.getSelectedItem().toString().toUpperCase());
					} catch (IllegalArgumentException e1) {
						JOptionPane.showMessageDialog(null, "Must enter a valid keyboard type", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					LayoutType layout;
					// check if keyboard layout input is valid
					try {
						layout = LayoutType.valueOf(keyboardLayoutCmbx.getSelectedItem().toString().toUpperCase());
					} catch (IllegalArgumentException e1) {
						JOptionPane.showMessageDialog(null, "Must enter a valid keyboard layout", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					Keyboard newItem = new Keyboard(barcode, keyboardType, brand, colour, connectivity, quantity,
							originalCost, retailPrice, layout);
					try {

						user.addProduct(newItem);

					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				updateStockTable();
			}
		});
		panel.add(addProductBtn);

		String[] keyboardTypes = { "Standard", "Gaming", "Flexible" };
		keyboardTypeCmbx = new JComboBox<Object>(keyboardTypes);
		keyboardTypeCmbx.setEnabled(false);
		keyboardTypeCmbx.setBounds(470, 49, 119, 21);
		panel.add(keyboardTypeCmbx);

		String[] keyboardLayouts = { "UK", "US" };
		keyboardLayoutCmbx = new JComboBox<Object>(keyboardLayouts);
		keyboardLayoutCmbx.setEnabled(false);
		keyboardLayoutCmbx.setBounds(470, 81, 119, 21);
		panel.add(keyboardLayoutCmbx);

		String[] mouseTypes = { "Standard", "Gaming" };
		mouseTypeCmbx = new JComboBox<Object>(mouseTypes);
		mouseTypeCmbx.setEnabled(false);
		mouseTypeCmbx.setBounds(470, 114, 119, 21);
		panel.add(mouseTypeCmbx);

		numberOfButtonsTxt = new JTextField();
		numberOfButtonsTxt.setEnabled(false);
		numberOfButtonsTxt.setBounds(470, 145, 31, 19);
		panel.add(numberOfButtonsTxt);
		numberOfButtonsTxt.setColumns(10);

		String[] connectivityTypes = { "Wired", "Wireless" };
		connectivityCmbx = new JComboBox<Object>(connectivityTypes);
		connectivityCmbx.setBounds(121, 116, 119, 17);
		panel.add(connectivityCmbx);

		keyboardTypeLbl = new JLabel("Keyboard Type");
		keyboardTypeLbl.setEnabled(false);
		keyboardTypeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		keyboardTypeLbl.setBounds(325, 53, 127, 13);
		panel.add(keyboardTypeLbl);

		keyboardLayoutLbl = new JLabel("Keyboard Layout");
		keyboardLayoutLbl.setEnabled(false);
		keyboardLayoutLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		keyboardLayoutLbl.setBounds(310, 82, 141, 13);
		panel.add(keyboardLayoutLbl);

		mouseTypeLbl = new JLabel("Mouse Type");
		mouseTypeLbl.setEnabled(false);
		mouseTypeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mouseTypeLbl.setBounds(363, 118, 97, 13);
		panel.add(mouseTypeLbl);

		numberOfButtonsLbl = new JLabel("Number of Buttons");
		numberOfButtonsLbl.setEnabled(false);
		numberOfButtonsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		numberOfButtonsLbl.setBounds(325, 148, 135, 13);
		panel.add(numberOfButtonsLbl);

		JButton clearInputFieldsBtn = new JButton("Clear Input Fields");
		// clear all the input fields
		clearInputFieldsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				barcodeTxt.setText("");
				brandTxt.setText("");
				colourTxt.setText("");
				connectivityCmbx.setSelectedIndex(0);
				quantityTxt.setText("");
				originalCostTxt.setText("");
				retailPriceTxt.setText("");
				productTypeCmbx.setSelectedIndex(-1);
				mouseTypeCmbx.setSelectedIndex(0);
				numberOfButtonsTxt.setText("");
				productTypeCmbx.setSelectedIndex(-1);
				mouseTypeCmbx.setSelectedIndex(0);
				keyboardTypeCmbx.setSelectedIndex(0);
				keyboardLayoutCmbx.setSelectedIndex(0);
				mouseTypeCmbx.setEnabled(false);
				numberOfButtonsTxt.setEnabled(false);
				keyboardTypeCmbx.setEnabled(false);
				keyboardLayoutCmbx.setEnabled(false);
				mouseTypeLbl.setEnabled(false);
				numberOfButtonsLbl.setEnabled(false);
				keyboardTypeLbl.setEnabled(false);
				keyboardLayoutLbl.setEnabled(false);
				numberOfButtonsTxt.setEnabled(false);
				keyboardTypeCmbx.setEnabled(false);
				keyboardLayoutCmbx.setEnabled(false);
			}
		});
		clearInputFieldsBtn.setBounds(633, 148, 168, 33);
		panel.add(clearInputFieldsBtn);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 50, 1166, 240);
		contentPane.add(panel_1);

		updateStockTable();

		panel_1.setLayout(null);

		table.setSurrendersFocusOnKeystroke(true);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 1166, 240);
		panel_1.add(scrollPane);

		JLabel stockLbl = new JLabel("Stock");
		stockLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stockLbl.setBounds(10, 17, 124, 23);
		contentPane.add(stockLbl);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Login frame = new Login();
				frame.setVisible(true);
				dispose();
			}
		});
		logoutBtn.setBounds(1082, 11, 94, 32);
		contentPane.add(logoutBtn);

		JLabel addProductsLbl = new JLabel("Add products");
		addProductsLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addProductsLbl.setBounds(10, 300, 226, 26);
		contentPane.add(addProductsLbl);

	}

	// displays the stock on a table
	public void updateStockTable() {
		String[] columnNames = { "BARCODE", "DEVICE NAME", "DEVICE TYPE", "BRAND", "COLOUR", "CONNECTIVITY",
				"QUANTITY IN STOCK", "ORIGINAL COST", "RETAIL PRICE", "ADDITIONAL INFO" };

		List<Product> stockList = user.getProducts();
		Object[][] data = new Object[stockList.size()][10];
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
			data[i][7] = item.getOriginalCost();
			data[i][8] = item.getRetailPrice();
			if (item.getDeviceName().equals("mouse")) {
				data[i][9] = ((Mouse) item).getNumberOfButtons() + " (# of buttons)";
			}
			if (item.getDeviceName().equals("keyboard")) {
				data[i][9] = ((Keyboard) item).getLayout() + " (layout)";
			}
		}
		stockTableModel.setDataVector(data, columnNames);
		table.clearSelection();
		stockTableModel.fireTableDataChanged();

	}
}
