/**
 *
 *   ********    ********      ********    								******************
 *  *           *        *    *        *   								********* ********
 *  *          *          *   *   *    *   								********   *******
 *    *****    *    **    *   *    * *     								*******     ******
 *          *  *          *   *   *    *   								******       *****
 *          *   *        *    *        *   								*****         ****
 *  ********     ********      ********    								******************
 */

package greetings;
/**
 * GreetingsApp
 * Say Hello to the world
 * of programming -
 * written by @author sofoste
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

@SuppressWarnings("unused")
public class GreetingsApp {

	public JFrame frame;
	private JTextField textField;

	/**
	 * Create the application.
	 */
	public GreetingsApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Greeting version 1.0 [sofoste]");
		frame.getContentPane().setForeground(new Color(0, 255, 0));
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		frame.setResizable(false);
		//frame.isResizable();

		textField = new JTextField();
		textField.setBackground(new Color(204, 204, 255));
		textField.setToolTipText("");
		textField.setForeground(new Color(0, 0, 0));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Consolas", Font.BOLD, 11));
		textField.setBounds(68, 11, 198, 30);
		frame.getContentPane().add(textField);
		textField.setColumns(10);


		JLabel label = new JLabel("");
		label.setForeground(new Color(0, 255, 0));
		label.setBackground(new Color(0, 0, 0));
		label.setBounds(20, 52, 366, 80);
		frame.getContentPane().add(label);

		/**
		 * action on my button
		 */
		JButton btnEnter = new JButton("ENTER");
		btnEnter.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnEnter.setBackground(new Color(153, 255, 153));
		/**
		 * let clear the textField since
		 * we got the userInput.
		 */
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//let get the text from the textField

				if(textField.getText().equals("")) {
					label.setText("<html>Hello user! I CAN'T GUESS YOUR NAME.<br><hr>Use the TextField to enter your name.<br></html>");
				} else {
					label.setText("Hello " + textField.getText().toUpperCase()+" !");
				}
				textField.setText(""); // clear the textField

				frame.getBackground();
				System.out.print("Program launched successfuly...\n");
			}
		});
		btnEnter.setBounds(291, 9, 133, 32);
		frame.getContentPane().add(btnEnter);

		JLabel lblNewLabel = new JLabel("Name: ");
		lblNewLabel.setForeground(new Color(0, 255, 0));
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setBounds(10, 11, 48, 30);
		frame.getContentPane().add(lblNewLabel);

		//close frame
		JButton btnClose = new JButton("CLOSE");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame.dispose();
			}
		});
		btnClose.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnClose.setBackground(new Color(255, 102, 102));
		btnClose.setBounds(347, 218, 77, 32);
		frame.getContentPane().add(btnClose);

	}
}
