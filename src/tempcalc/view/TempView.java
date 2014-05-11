/**
 * Temperature-unit converter based on given mockup-design
 * @author Philip L
 **/

package tempcalc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tempcalc.controller.TempControllerInterface;
import tempcalc.model.TempModelInterface;

public class TempView implements ActionListener, ChangeListener, StatObserver,
		TempViewInterface {
	// object-references:
	private TempModelInterface model;
	private TempControllerInterface controller;

	// GUI elements
	private JFrame viewFrame;
	private JPanel leftPanel;
	private JPanel middlePanel;
	private JPanel rightPanel;
	private JPanel gridPanel;
	private JSlider fahrSlider;
	private JSlider celsSlider;
	private JLabel textOutput;
	private JTextField textInput;
	private JButton switchUnit;
	private JButton convert;

	// JSlider-constants:
	static final int FAHR_MIN = 0;
	static final int CELS_MIN = 0;
	static final int FAHR_MAX = 212;
	static final int CELS_MAX = 100;
	static final int FAHR_INITIAL = 32;
	static final int CELS_INITIAL = 0;

	public TempView(TempControllerInterface controller, TempModelInterface model) {
		this.controller = controller;
		this.model = model;
		model.registerObserver((StatObserver) this);
	}

	/** Method functioning as constructor for one window */
	public void createView() {
		viewFrame = new JFrame("F°/C° Converter");
		viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(5, 1));
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		fahrSlider = new JSlider(JSlider.VERTICAL, FAHR_MIN, FAHR_MAX,
				FAHR_INITIAL);
		fahrSlider.setMajorTickSpacing(40);
		fahrSlider.setMinorTickSpacing(10);
		fahrSlider.createStandardLabels(1); // creates HashTable for each (1)
											// int from min to max
		fahrSlider.setPaintTicks(true);
		fahrSlider.setPaintLabels(true);

		celsSlider = new JSlider(JSlider.VERTICAL, CELS_MIN, CELS_MAX,
				CELS_INITIAL);
		celsSlider.setMajorTickSpacing(20);
		celsSlider.setMinorTickSpacing(5);
		celsSlider.createStandardLabels(1); // creates HashTable for each (1)
											// int from min to max
		celsSlider.setPaintTicks(true);
		celsSlider.setPaintLabels(true);

		leftPanel.add(new JLabel("°F"));
		leftPanel.add(fahrSlider);
		leftPanel.add(new JLabel("°F"));
		rightPanel.add(new JLabel("°C"));
		rightPanel.add(celsSlider);
		rightPanel.add(new JLabel("°C"));

		textOutput = new JLabel("Use slider or type");
		textOutput.setFont(new Font("Ariel", Font.BOLD, 18));
		textOutput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textOutput.setHorizontalAlignment(SwingConstants.CENTER);

		textInput = new JTextField("0", 5);

		textInput.requestFocus();
		switchUnit = new JButton("C°");
		gridPanel = new JPanel(new GridLayout());
		convert = new JButton("Convert!");

		convert.setFont(new Font("Ariel", Font.BOLD, 20));

		middlePanel.add(Box.createVerticalStrut(5));
		middlePanel.add(textOutput);
		gridPanel.add(textInput);
		gridPanel.add(switchUnit);
		gridPanel.setBorder(BorderFactory
				.createTitledBorder("Set value and unit"));
		middlePanel.add(gridPanel);
		middlePanel.add(convert);

		viewFrame.add(leftPanel, BorderLayout.WEST);
		viewFrame.add(rightPanel, BorderLayout.EAST);
		viewFrame.add(middlePanel, BorderLayout.CENTER);
		viewFrame.add(new JLabel(" "), BorderLayout.SOUTH);

		fahrSlider.addChangeListener((ChangeListener) this);
		celsSlider.addChangeListener((ChangeListener) this);
		textInput.addActionListener((ActionListener) this);
		switchUnit.addActionListener((ActionListener) this);
		convert.addActionListener((ActionListener) this);

		viewFrame.pack();
		// put window in the center of the screen; you have to invoke pack()
		// first:
		viewFrame.setLocationRelativeTo(null);

		JFrame.setDefaultLookAndFeelDecorated(true);
		viewFrame.getRootPane().setDefaultButton(convert);
		viewFrame.setResizable(false);
		viewFrame.setVisible(true);
	}

	@Override
	public void updateStat() {
		double cels = (double) model.getCels();
		double fahr = (double) model.getFahr();
		controller.updateView(cels, fahr);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object widget = (Object) event.getSource();

		if (widget == switchUnit) {
			controller.switchUnit();
		} else if ((widget == convert) || (widget == textInput)) {
			boolean b = controller.checkInput(getInputText());
			if (b) {
				controller.convert();
			}
		} else
			return;
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		JSlider jslider = (JSlider) event.getSource();

		// actual parameter true for fahrSlider, false for celsSlider
		if (jslider == fahrSlider) {
			if (!fahrSlider.getValueIsAdjusting()) {
				controller.sliderReact(fahrSlider.getValue(), true);
				System.out.println("°F: " + fahrSlider.getValue());
			}
		} else if (jslider == celsSlider) {
			if (!celsSlider.getValueIsAdjusting()) {
				controller.sliderReact(celsSlider.getValue(), false);
				System.out.println("°C: " + celsSlider.getValue());
			}
		} else
			return;
	}

	public void changeSwitchButton(boolean isC) {
		if (isC) {
			switchUnit.setText("°F");
		} else {
			switchUnit.setText("°C");
		}
	}

	public String getInputText() {
		return textInput.getText();
	}

	public void changeInputText(String s) {
		textInput.setText(s);
	}

	public int getFahrSliderValue() {
		return fahrSlider.getValue();
	}

	public void setFahrSliderValue(int i) {
		fahrSlider.setValue(i);
	}

	public int getCelsSliderValue() {
		return celsSlider.getValue();
	}

	public void setCelsSliderValue(int i) {
		celsSlider.setValue(i);
	}

	public void displayText(String s) {
		textOutput.setForeground(Color.BLACK);
		textOutput.setText(s);
	}

	public void displayWarning(String s) {
		textOutput.setForeground(Color.RED);
		textOutput.setText(s);
	}
}
