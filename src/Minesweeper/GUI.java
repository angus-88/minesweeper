package Minesweeper;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DropMode;
import java.awt.Font;

public class GUI {

	private JFrame frame;
	private JTextField txtInput;



	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 615, 675);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		
		JButton btnNewGame = new JButton("Submit");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewGame.setBounds(262, 450, 89, 23);
		getFrame().getContentPane().add(btnNewGame);
		
		JLabel lblEnterAGrid = new JLabel("Enter a Grid Size between 5 and 26");
		lblEnterAGrid.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblEnterAGrid.setBounds(57, 77, 460, 79);
		frame.getContentPane().add(lblEnterAGrid);
		
		txtInput = new JTextField();
		txtInput.setBounds(262, 358, 86, 20);
		frame.getContentPane().add(txtInput);
		txtInput.setColumns(10);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
