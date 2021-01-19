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

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * @author sofoste
 * GreetingsApp
 */
@SuppressWarnings("unused")
public class Main extends GreetingsApp {

	/**
	 * Launch the application.
	 * run the app
	 * @param args
	 */
	public static void main(String[] args) {

		//GreetingsApp g = new GreetingsApp();
		//g.frame = new JFrame();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GreetingsApp window = new GreetingsApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
