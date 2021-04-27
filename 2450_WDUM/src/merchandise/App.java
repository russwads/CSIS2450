package merchandise;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.JToggleButton;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JInternalFrame;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.DropMode;

public class App {

	private int purchNum = 1;
	private JFrame wduFrame;
	private JTabbedPane complete;
	private JTextField textField;
	private JTextArea receipt;
	private JLabel total;
	private static ArrayList<Product> prods = new ArrayList<Product>();
	// Creating input channels
    private final static String connectionString = "jdbc:mysql://127.0.0.1:3306/disneyuniverse";
    private final static String dbLogin = "root";
    private final static String dbPassword = "DBAdmin";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {        
        // SQL Data
		String sqlSelectProd = "SELECT upc,name,stock,price FROM disneyuniverse.products WHERE store_id = 1;";
		Connection conn = null;
		
		// Connection to MySQL DB
		try {
            conn = DriverManager.getConnection(connectionString, dbLogin, dbPassword);
			if (conn != null) {
	            try (Statement stmt = conn.createStatement(
	                     ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                     ResultSet.CONCUR_UPDATABLE);
	                 ResultSet rs = stmt.executeQuery(sqlSelectProd)) {
	            	// Get full SQL data for number of rows
	                rs.last();
	                int numRows = rs.getRow();
	                rs.first();
	                for (int i = 0; i < numRows; i++) {
	                	prods.add(new Product(rs.getLong(1), rs.getString(2), rs.getInt(3), rs.getDouble(4)));
	                	rs.next();
	                }
	            } catch (SQLException e) {
					// Failed SQL Generation
	            	// IMPORTANT!! The software must correctly connect to the database and populate store items
	            	JOptionPane.showMessageDialog(null, "Fatal Error: SQL Products failed.\nPlease contact system administrator.", "SQL Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					for (int i = 0; i < prods.size(); i++) {
						prods.get(i).toString();
					}
				}
			}
			conn.close();
		}
		catch (Exception e) {
        	// Failed connection terminates the software completely
        	// IMPORTANT!! The software will not work without a secure DB connection
        	JOptionPane.showMessageDialog(null, "Fatal Error: Connection to SQL DataBase failed.\nPlease contact system administrator.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					App window = new App();
					window.wduFrame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Order ord = new Order();
		wduFrame = new JFrame();
		wduFrame.setTitle("Walt Disney Universe Merchandising");
		wduFrame.setResizable(false);
		wduFrame.setBounds(100, 100, 800, 665);
		wduFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        wduFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/merchandise/mickey-mouse-logo-mouse-icon.jpg")));
        wduFrame.getContentPane().setLayout(null);
        
        complete = new JTabbedPane(JTabbedPane.TOP);
        complete.setFont(new Font("Bahnschrift", complete.getFont().getStyle(), 20));
        complete.setBounds(0, 0, 794, 639);
        wduFrame.getContentPane().add(complete);
        
        purchase(complete, ord);
        returning(complete);
        exchanges(complete);
        administration(complete);
        
        JPanel returning = new JPanel();
        complete.addTab("Return", null, returning, null);
        complete.setEnabledAt(1, false);
        
        JPanel exchange = new JPanel();
        complete.addTab("Exchange", null, exchange, null);
        complete.setEnabledAt(2, false);
        
        JPanel settings = new JPanel();
        complete.addTab("Settings", null, settings, null);
        complete.setEnabledAt(3, false);
        
        JPanel admin = new JPanel();
        complete.addTab("Administration", null, admin, null);
        
        JLabel bkgdAdmin = new JLabel("");
        bkgdAdmin.setIcon(new ImageIcon(App.class.getResource("/merchandise/gradient.png")));
        admin.add(bkgdAdmin);
	}
	/**
	 * Panel used for administration of MySQL DB
	 * TODO ALL functionality
	 * @param tabbedPane full window application
	 */
	private void administration(JTabbedPane tabbedPane) {
		
	}
	/**
	 * Panel used to exchange products
	 * TODO ALL functionality
	 * @param tabbedPane full window application
	 */
	private void exchanges(JTabbedPane tabbedPane) {
	}
	/**
	 * Panel used for purchasing products and finalizing orders
	 * @param tabbedPane full window application
	 * @param o Order used throughout the application
	 */
	private void purchase(JTabbedPane tabbedPane, Order o) {
		
		JPanel purchase = new JPanel();
		complete.addTab("Purchase", null, purchase, null);
		purchase.setLayout(null);
		
		keypad(purchase, o);
		
		JLabel picLogo = new JLabel("");
		picLogo.setIcon(new ImageIcon(App.class.getResource("/merchandise/wdu2.png")));
		picLogo.setBounds(0, 0, 251, 150);
		purchase.add(picLogo);
		
		JLabel lblPerner = new JLabel("PERNER#: 05151928");
		lblPerner.setBounds(263, 18, 175, 35);
		purchase.add(lblPerner);
		
		JLabel lblName = new JLabel("Name: Mickey Mouse");
		lblName.setBounds(263, 49, 175, 16);
		purchase.add(lblName);
		
		upcEntry(purchase);
		
		total = new JLabel("Subtotal: $0.00");
		total.setBackground(Color.WHITE);
		total.setFont(new Font("Bookman Old Style", Font.BOLD, 30));
		total.setHorizontalAlignment(SwingConstants.CENTER);
		total.setBounds(450, 364, 302, 55);
		purchase.add(total);
		
		receipt = new JTextArea();
		receipt.setEnabled(false);
		receipt.setEditable(false);
		receipt.setBounds(35, 335, 346, 228);
		purchase.add(receipt);
		
		JButton cashBtn = new JButton("Cash");
		cashTransaction(o, cashBtn);
		cashBtn.setBounds(450, 431, 86, 36);
		purchase.add(cashBtn);
		
		JButton ccBtn = new JButton("Credit Card");
		ccBtn.setEnabled(false);
		ccBtn.setToolTipText("Unavailable for this build");
		ccBtn.setBounds(554, 431, 100, 36);
		purchase.add(ccBtn);
		
		JButton magicBtn = new JButton("Magic");
		magicBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				o.discountOverride(Discount.THREE);
				double subt = o.getPrice();
				o.priceCalc();
				double salestax = Math.round((subt * 0.06199) * 100.0) / 100; 
				double final_price = o.getPrice();
				printReceipt(o, subt, salestax, final_price, "MAGIC");
			}
		});
		magicBtn.setBounds(666, 431, 86, 36);
		purchase.add(magicBtn);
		
		JLabel bkgdPurch = new JLabel("");
		bkgdPurch.setBounds(0, 0, 790, 600);
		bkgdPurch.setIcon(new ImageIcon(App.class.getResource("/merchandise/gradient.png")));
		purchase.add(bkgdPurch);
	}

	/**
	 * Completes the transaction through inserting cash into the till and returning change
	 * @param o Order used throughout the application
	 * @param cashBtn JButton pressed to initiate cash transaction
	 */
	private void cashTransaction(Order o, JButton cashBtn) {
		cashBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Subtotal, taxes, and calculations for total due
				double subt = o.getPrice();
				o.priceCalc();
				double salestax = Math.round((subt * 0.06199) * 100.0) / 100; 
				double final_price = o.getPrice();
				
				// Create external panel for cash input
				Cash c = new Cash(final_price);
				printReceipt(o, subt, salestax, final_price, "CASH");
			}

			
		});
	}
	/**
	 * Prints receipt and saves the recipes into a .txt file
	 * @param o order used throughout application
	 * @param subt subtotal
	 * @param salestax sales tax
	 * @param final_price final price
	 */
	private void printReceipt(Order o, double subt, double salestax, double final_price, String type) {
		String receiptFin = "WALT DISNEY UNIVERSE\nWORLD OF DISNEY\nPAROWAN, UT 84761\n\n" + receipt.getText()
				+ String.format("\n\nSUBTOTAL: $%.2f\nSALES TAX: $%.2f\nTOTAL: $%.2f\n\nTENDER: " + type + " $%.2f"
				+ "\n\nTHANK YOU FOR YOUR PATRONAGE\nHAVE A MAGICAL DAY!"
				+ "\n0001  MMouse  ", subt, salestax, final_price, final_price)
				+ o.getDateAndTime();
		
		// Printing receipt (saves as .txt file in program files)
		FileWriter fw;
		try {
			fw = new FileWriter("Receipt_" + purchNum + ".txt", false);
			fw.write(receiptFin);
    		fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Order number increase, reset text in purchase window
		purchNum = purchNum + 1;
		receipt.setText("");
		total.setText("Subtotal: $0.00");
		
		// Insert order into MySQL DB and reset order in application
		orderInsertSQL(o);
		o.empty();
	}
	/**
	 * Inserts completed orders into the SQL DB
	 * @param o order used throughout application
	 */
	private void orderInsertSQL(Order o) {
		Connection conn = null;
		String sqlInsertOrder = "INSERT INTO orders(price, date, time, discount, store_id)\nVALUES(" + o.toString();
		// Connection to MySQL DB
		try {
            conn = DriverManager.getConnection(connectionString, dbLogin, dbPassword);
			if (conn != null) {
	            try (Statement stmt = conn.createStatement()) {
	                stmt.executeUpdate(sqlInsertOrder);
					conn.close();
	            } catch (SQLException e1) {
					// Failed SQL Generation
	            	// IMPORTANT!! The software must correctly connect to the database and insert new order
	            	JOptionPane.showMessageDialog(null, "Fatal Error: SQL Insertion failed.\nPlease contact system administrator.", "SQL Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		}
		catch (Exception exc) {
        	// Failed connection terminates the software completely
        	// IMPORTANT!! The software will not work without a secure DB connection
        	JOptionPane.showMessageDialog(null, "Fatal Error: Connection to SQL DataBase failed.\nPlease contact system administrator.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            exc.printStackTrace();
        }
	}
	/**
	 * Panel placement for inputting UPC labels
	 * @param purchase purchase panel in multi-panel application
	 */
	private void upcEntry(JPanel purchase) {
		// Label for scanning/entering UPC
		// Scan API not available for this build
		JLabel upcField = new JLabel("Scan or Enter UPC#:");
		upcField.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		upcField.setBounds(35, 228, 336, 24);
		purchase.add(upcField);
		
		// Input for UPC
		textField = new JTextField();
		upcField.setLabelFor(textField);
		textField.setFont(new Font("SansSerif", Font.PLAIN, 30));
		textField.setBounds(36, 256, 349, 40);
		purchase.add(textField);
		textField.setColumns(10);
	}
	/**
	 * A 0-9 keypad on-screen to manually enter UPCs and PERNER ID's (the latter is for discounts).
	 * When the user presses "Clear," the current UPC clears in the typing field. The input for 
	 * products is limited to either 8 digits, 12 digits, or a very specific 9 digit code for "Christmas."
	 * The valid UPCs are found in the database, but to practice, use the following UPC codes as
	 * practice: 266421175647, 294589446815, 237228876781, 279446108512, and 234855713325.
	 * TODO Workforce connection so all 8-digit codes will not create a 20% discount
	 * @param purchase purchase panel for multi-panel application
	 * @param ord order object used throughout application
	 */
	private void keypad(JPanel purchase, Order ord) {
		JButton btnOne = new JButton("1");
		btnOne.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = textField.getText();
				textField.setText(x + "1");
			}
		});
		btnOne.setBounds(450, 49, 100, 75);
		purchase.add(btnOne);
		
		JButton btnTwo = new JButton("2");
		btnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = textField.getText();
				textField.setText(x + "2");
			}
		});
		btnTwo.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		btnTwo.setBounds(551, 49, 100, 75);
		purchase.add(btnTwo);
		
		JButton btnThree = new JButton("3");
		btnThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = textField.getText();
				textField.setText(x + "3");
			}
		});
		btnThree.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		btnThree.setBounds(652, 49, 100, 75);
		purchase.add(btnThree);
		
		JButton btnFour = new JButton("4");
		btnFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = textField.getText();
				textField.setText(x + "4");
			}
		});
		btnFour.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		btnFour.setBounds(450, 125, 100, 75);
		purchase.add(btnFour);
		
		JButton btnFive = new JButton("5");
		btnFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = textField.getText();
				textField.setText(x + "5");
			}
		});
		btnFive.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		btnFive.setBounds(551, 125, 100, 75);
		purchase.add(btnFive);
		
		JButton btnSix = new JButton("6");
		btnSix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = textField.getText();
				textField.setText(x + "6");
			}
		});
		btnSix.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		btnSix.setBounds(652, 125, 100, 75);
		purchase.add(btnSix);
		
		JButton btnSeven = new JButton("7");
		btnSeven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = textField.getText();
				textField.setText(x + "7");
			}
		});
		btnSeven.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		btnSeven.setBounds(450, 201, 100, 75);
		purchase.add(btnSeven);
		
		JButton btnEight = new JButton("8");
		btnEight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = textField.getText();
				textField.setText(x + "8");
			}
		});
		btnEight.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		btnEight.setBounds(551, 201, 100, 75);
		purchase.add(btnEight);
		
		JButton btnNine = new JButton("9");
		btnNine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = textField.getText();
				textField.setText(x + "9");
			}
		});
		btnNine.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		btnNine.setBounds(652, 201, 100, 75);
		purchase.add(btnNine);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
			}
		});
		btnClear.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		btnClear.setBounds(450, 277, 100, 75);
		purchase.add(btnClear);
		
		JButton btnZero = new JButton("0");
		btnZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = textField.getText();
				textField.setText(x + "0");
			}
		});
		btnZero.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		btnZero.setBounds(551, 277, 100, 75);
		purchase.add(btnZero);
		
		JButton btnEnter = new JButton("Enter");
		// Key-binding: Pressing 'Enter' on keyboard is same as pressing 
		btnEnter.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		Action act = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String x = textField.getText();
				if (isInt(x)) {
					if (x == "247478627") {
						JOptionPane.showMessageDialog(null, "40% Christmas Discount Applied.", "Discount Information", JOptionPane.INFORMATION_MESSAGE);
						// Apply Discount to Order
						ord.discountOverride(Discount.TWO);
						textField.setText("");
					}
					// Employee PERNER Number length == Check for correct PERNER
					else if (x.length() == 8) {
						JOptionPane.showMessageDialog(null, "Employee PERNER # Entered.\n20% Discount Applied.", "Discount Information", JOptionPane.INFORMATION_MESSAGE);
						// Apply Discount to Order
						ord.discountOverride(Discount.ONE);
						textField.setText("");
					}
					else if (x.length() == 12) {
						// UPC check
						long u = Long.parseLong(x);
						int i = 0;
						// Find valid products
						while (u != prods.get(i).getUPC() && i != prods.size() - 1) { i++; }
						if (u == prods.get(i).getUPC()) {
							ord.addProduct(prods.get(i));
							receipt.setText(receipt.getText() + "\n" + prods.get(i).toString());
							String p = String.format("%.2f", ord.getPrice());
							total.setText("Subtotal: $" + p);
							textField.setText("");
						}
						else {
							JOptionPane.showMessageDialog(null, "Invalid UPC: Please Try Again", "Input Error", JOptionPane.ERROR_MESSAGE);
							textField.setText("");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Not a Valid Length: Please Enter Valid UPC of 12 Numbers", "Input Error", JOptionPane.ERROR_MESSAGE);
						textField.setText("");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Letters Not Accepted: Only Numbers Allowed", "Input Error", JOptionPane.ERROR_MESSAGE);
					textField.setText("");
				}
			}
		};
		btnEnter.getActionMap().put("Enter", act);
		btnEnter.addActionListener(act);
		btnEnter.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		btnEnter.setBounds(652, 277, 100, 75);
		purchase.add(btnEnter);
	}
	/**
	 * Check to see if UPC input contains any letters or if it is just a straight integer 
	 * @param x UPC string
	 * @return false if there is any letters A-Z, a-z; true otherwise
	 */
	private boolean isInt(String x) {
		for (int i = 0; i < x.length(); i++) {
			// If there is any letter in the string, bool returns false.
			if (Character.isLetter(x.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Panel used to return products
	 * TODO ALL functionality
	 * @param tabbedPane returning products
	 */
	private void returning(JTabbedPane tabbedPane) {
	}
}