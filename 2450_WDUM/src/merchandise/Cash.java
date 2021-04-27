package merchandise;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D.Double;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Cash extends JDialog {

	/**
	 * Launch the application.
	 */
	private double totalMoney = 0.0;
	private double buffTot = 0.0;
	private double paid = 0.0;
	private JDialog dialog = new JDialog();
	private JLabel totalRemaining;
	
	public Cash(double total) {
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		totalMoney = totalMoney + total;
		buffTot = buffTot + total;
		if (totalMoney == 0) { dialog.dispose(); }
		try {
			dialog.setIconImage(Toolkit.getDefaultToolkit().getImage(Cash.class.getResource("/merchandise/mickey-mouse-logo-mouse-icon.jpg")));
			dialog.setVisible(true);
			dialog.setBounds(100, 100, 450, 300);
			dialog.getContentPane().setLayout(null);
			go();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns the change amount when the window shuts down.
	 * @return
	 */
	public double getChange() {
		return paid - buffTot;
	}
	/**
	 * Closes the window.
	 */
	public void close() {
		dialog.dispose();
	}

	/**
	 * Create the dialog.
	 */
	public void go() {
		totalRemaining = new JLabel(String.format("Total Remaining: $%.2f", totalMoney));
		totalRemaining.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		totalRemaining.setBounds(0, 11, 340, 38);
		totalRemaining.setHorizontalAlignment(SwingConstants.CENTER);
		dialog.getContentPane().add(totalRemaining);
		
		JPanel moneyPanel = new JPanel();
		moneyPanel.setBounds(0, 50, 434, 211);
		dialog.getContentPane().add(moneyPanel);
		moneyPanel.setLayout(null);
		
		JButton hundred = new JButton("$100");
		hundred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalMoney = totalMoney - 100;
				paid = paid + 100;
				dialogRepaint();
			}
		});
		hundred.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		hundred.setBounds(34, 17, 89, 60);
		moneyPanel.add(hundred);
		
		JButton twenty = new JButton("$20");
		twenty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalMoney = totalMoney - 20;
				paid = paid + 20;
				dialogRepaint();
			}
		});
		twenty.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		twenty.setBounds(289, 17, 89, 60);
		moneyPanel.add(twenty);
		
		JButton fifty = new JButton("$50");
		fifty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalMoney = totalMoney - 50;
				paid = paid + 50;
				dialogRepaint();
			}
		});
		fifty.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		fifty.setBounds(161, 17, 89, 60);
		moneyPanel.add(fifty);
		
		JButton ten = new JButton("$10");
		ten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalMoney = totalMoney - 10;
				paid = paid + 10;
				dialogRepaint();
			}
		});
		ten.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		ten.setBounds(34, 80, 89, 60);
		moneyPanel.add(ten);
		
		JButton five = new JButton("$5");
		five.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalMoney = totalMoney - 5;
				paid = paid + 5;
				dialogRepaint();
			}
		});
		five.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		five.setBounds(161, 80, 89, 60);
		moneyPanel.add(five);
		
		JButton one = new JButton("$1");
		one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalMoney = totalMoney - 1;
				paid = paid + 1;
				dialogRepaint();
			}
		});
		one.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		one.setBounds(289, 80, 89, 60);
		moneyPanel.add(one);
		
		JButton quarter = new JButton(".25");
		quarter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalMoney = totalMoney - 0.25;
				paid = paid + 0.25;
				dialogRepaint();
			}
		});
		quarter.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		quarter.setBounds(23, 155, 89, 45);
		moneyPanel.add(quarter);
		
		JButton dime = new JButton(".10");
		dime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalMoney = totalMoney - 0.1;
				paid = paid + 0.1;
				dialogRepaint();
			}
		});
		dime.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		dime.setBounds(122, 155, 89, 45);
		moneyPanel.add(dime);
		
		JButton nickel = new JButton(".05");
		nickel.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		nickel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalMoney = totalMoney - 0.05;
				paid = paid + 0.05;
				dialogRepaint();
			}
		});
		nickel.setBounds(221, 155, 89, 45);
		moneyPanel.add(nickel);
		
		JButton penny = new JButton(".01");
		penny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalMoney = totalMoney - 0.01;
				paid = paid + 0.01;
				dialogRepaint();
			}
		});
		penny.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		penny.setBounds(320, 155, 89, 45);
		moneyPanel.add(penny);
	}
	/**
	 * Repeating repaint when buttons are pressed to indicate changes in cash payments.
	 */
	public void dialogRepaint() {
		totalRemaining.setText(String.format("Total Remaining: $%.2f", totalMoney));
		if (totalMoney < 0.01) {
			totalRemaining.setText(String.format("Change: $%.2f", Math.abs(totalMoney)));
			JOptionPane.showMessageDialog(null, "Drawer is opening. Press OK to Continue.", "Drawer Open", JOptionPane.INFORMATION_MESSAGE);
			close();
		}
	}
}