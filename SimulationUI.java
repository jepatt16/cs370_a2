import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SimulationUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	// define constants
	public static final String[] menuStrings = {"Number of Workers", "Queue Size", "Number of Requests", "Arrival Rate", "Service Rate"};
	public static final String[] defaultValues = {"1", "10", "100", "10", "10"};
	public static final int[] minValues = {1, 1, 10, 1, 1};
	public static final int[] maxValues = {10, 100, 1000000, 100, 100};

	// instance variables for the JPanel
	private JFrame parentFrame;
	private JTextField[] values;
	private JTextArea console;
	private JButton startButton, resetButton;

	public SimulationUI(JFrame parent) {
		// link the parent frame
		parentFrame = parent;

		// set the preferred size to 325 x 225 pixels
		super.setPreferredSize(new Dimension(325, 225));

		// use the BorderLayout for the panel
		super.setLayout(new BorderLayout());

		// create the top menu panel for the simulation parameters
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(5, 2));

		values = new JTextField[menuStrings.length];

		for (int i = 0; i < menuStrings.length; i++) {
			topPanel.add(new JLabel(menuStrings[i], SwingConstants.CENTER));
			values[i] = new JTextField(defaultValues[i]);
			values[i].setHorizontalAlignment(JTextField.RIGHT);
			topPanel.add(values[i]);
		}

		super.add(topPanel, BorderLayout.PAGE_START);

		// use a text area to display simulation result
		console = new JTextArea();

		console.setEditable(false);

		super.add(console, BorderLayout.CENTER);

		// create the bottom buttons
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));

		startButton = new JButton("Start Simulation!");
		resetButton = new JButton("Reset Parameters");

		startButton.addActionListener(this);
		resetButton.addActionListener(this);

		bottomPanel.add(startButton);
		bottomPanel.add(resetButton);

		super.add(bottomPanel, BorderLayout.PAGE_END);

	}

	// check if there are valid values for each simulation parameter
	private boolean checkValidValues() {
		int value;
		String warningMessage = "";
		for (int i = 0; i < menuStrings.length; i++) {
			try {
				value = Integer.parseInt(values[i].getText());
			} catch (NumberFormatException e) {
				warningMessage = "You must input an integer for " + menuStrings[i];
				break;
			}
			if (value < minValues[i]) {
				warningMessage = "You must input an integer great than or equal to " + minValues[i] + " for " + menuStrings[i];
				break;
			}
			if (value > maxValues[i]) {
				warningMessage = "You must input an integer less than or equal to " + maxValues[i] + " for " + menuStrings[i];
				break;
			}
		}
		if (warningMessage.equals("")) {
			return true;
		}
		JOptionPane.showMessageDialog(parentFrame,
				warningMessage,
				"Parameter Error",
				JOptionPane.WARNING_MESSAGE);
		return false;
	}

	public void setButtons(boolean enabled) {
		// enable or disable buttons
		startButton.setEnabled(enabled);
		resetButton.setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// if the start button is activated
		if (event.getSource() == startButton) {
			if (checkValidValues()) {
				setButtons(false);
				// capture all the simulation parameters
				int numWorkers = Integer.parseInt(values[0].getText());
				int queueSize = Integer.parseInt(values[1].getText());
				int numRequests = Integer.parseInt(values[2].getText());
				int arrivalRate = Integer.parseInt(values[3].getText());
				int serviceRate = Integer.parseInt(values[4].getText());

				// create and start a Simulation thread object using the appropriate parameters
				Simulation s = new Simulation(numWorkers, queueSize, numRequests, arrivalRate, serviceRate, this);
				setConsole("Running the simulation. Please wait...");
				s.start();
			}
		}

		// if the reset button is activated
		if (event.getSource() == resetButton) {
			// set everything to default values
			for (int i = 0; i < menuStrings.length; i++) {
				values[i].setText(defaultValues[i]);
			}
		}

	}

	// method to set the text on the output console
	public void setConsole(String text) {
		console.setText(text);
	}

	// run the program
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Producer and Consumer Simulation");
		myFrame.getContentPane().add(new SimulationUI(myFrame));;
		myFrame.pack();
		myFrame.setVisible(true);
	}

}
