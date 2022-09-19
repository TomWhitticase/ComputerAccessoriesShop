import java.awt.Color;
import java.awt.Container;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

	private User userSelected;

	private JPanel contentPane;

	public Login() {
		setResizable(false);
		setTitle("Computer Accessories Shop (Login)");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/images/mouse.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setBounds(200, 200, 500, 300);

		Container ControlHost = getContentPane();

		// get list of users
		List<User> userList = Database.getUserList();
		String[] userNameArray = new String[userList.size()];
		for (int i = 0; i < userList.size(); i++)
			userNameArray[i] = userList.get(i).getUsername();
		contentPane.setLayout(null);

		JButton loginBtn = new JButton("Login");
		loginBtn.setBounds(156, 169, 167, 29);
		ControlHost.add(loginBtn);

		JComboBox<Object> selectUserCmbx = new JComboBox<Object>(userNameArray);
		selectUserCmbx.setBounds(156, 122, 167, 21);
		selectUserCmbx.setSelectedIndex(-1);
		contentPane.add(selectUserCmbx);

		JLabel selectUserLbl = new JLabel("Select user:");
		selectUserLbl.setBounds(156, 102, 80, 13);
		contentPane.add(selectUserLbl);

		JLabel casTitleLbl = new JLabel("Computer Accessories Shop");
		casTitleLbl.setForeground(Color.BLUE);
		casTitleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		casTitleLbl.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.ITALIC, 28));
		casTitleLbl.setBounds(10, 10, 466, 58);
		contentPane.add(casTitleLbl);

		// event listener for combobox
		selectUserCmbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				userSelected = userList.get(selectUserCmbx.getSelectedIndex());
			}

		});

		// event listener for login button
		loginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (userSelected == null) {
					JOptionPane.showMessageDialog(null, "No user selected", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					boolean isAdmin = userSelected.getRole().equals("admin");
					if (isAdmin) {
						// open admin window
						AdminInterface adminInterface = new AdminInterface((Admin) userSelected);
						adminInterface.setVisible(true);
						dispose();
					} else {
						// open customer window
						CustomerInterface customerInterface = new CustomerInterface((Customer) userSelected);
						customerInterface.setVisible(true);
						dispose();
					}

				}

			}
		});

	}
}
