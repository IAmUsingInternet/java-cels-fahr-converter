/**
 * Temperature-unit converter based on given mockup-design
 * @author Philip L
 **/
package tempcalc.controller;

import java.text.DecimalFormat;

import tempcalc.model.TempModelInterface;
import tempcalc.view.TempView;
import tempcalc.view.TempViewInterface;

public class TempController implements TempControllerInterface {

	private TempModelInterface model;
	private TempViewInterface view;
	private boolean isFahrenheit;

	/**
	 * Constructor of the Controller. Assigns references to Model and View,
	 * passes them over and initializes them.
	 */
	public TempController(TempModelInterface model) {
		this.model = model;
		this.view = new TempView(this, model);
		view.createView();
	}

	@Override
	public void switchUnit() {
		try {
			if (isFahrenheit) {
				isFahrenheit = false;
				view.changeSwitchButton(true);
				model.setCels(Integer.parseInt(view.getInputText()
						.replaceAll("[^0-9]", "").trim()));
			} else if (!isFahrenheit) {
				isFahrenheit = true;
				view.changeSwitchButton(false);
				model.setFahr(Integer.parseInt(view.getInputText()
						.replaceAll("[^0-9]", "").trim()));
			}
		} catch (Exception e) {
			view.displayWarning("No valid input");
		}
	}

	@Override
	public void convert() {
		if (isFahrenheit) {
			model.setFahr(Integer.parseInt(view.getInputText()
					.replaceAll("[^0-9]", "").trim()));
		} else {
			model.setCels(Integer.parseInt(view.getInputText()
					.replaceAll("[^0-9]", "").trim()));
		}
	}

	/**
	 * Handler for JSliders
	 * 
	 * @param value
	 *            temperature-value
	 * @param isfahrenheit
	 *            true for fahrSlider, false for celsSlider
	 */
	@Override
	public void sliderReact(int value, boolean isFahrenheit) {
		if (isFahrenheit) {
			model.setFahr(value);
			this.isFahrenheit = !isFahrenheit;
			view.changeSwitchButton(false);
		} else {
			model.setCels(value);
			this.isFahrenheit = !isFahrenheit;
			view.changeSwitchButton(true);
		}
	}

	@Override
	public void updateView(double cels, double fahr) {
		DecimalFormat deci = new DecimalFormat("#.##");
		if (isFahrenheit) {
			view.displayText(deci.format(fahr) + "°F = " + deci.format(cels)
					+ "°C");

			Integer intWrapper = new Integer((int) fahr);
			String s = intWrapper.toString();
			view.changeInputText(s);
			view.setFahrSliderValue((int) fahr);
		} else if (!isFahrenheit) {
			view.displayText(deci.format(cels) + "°C = " + deci.format(fahr)
					+ "°F");

			Integer intWrapper = new Integer((int) cels);
			String s = intWrapper.toString();
			view.changeInputText(s);
			view.setCelsSliderValue((int) cels);
		}
	}

	@Override
	public boolean getIsFahrenheit() {
		return isFahrenheit;
	}

	@Override
	public boolean checkInput(String text) {
		Integer inputInteger = null;
		if (text.matches("[^0-9]*")) {
			view.displayWarning("No valid input");
			return false;
		} else {
			try {
				inputInteger = Integer.parseInt(text.replaceAll("[^0-9]", "")
						.trim());
				view.changeInputText(inputInteger.toString());
			} catch (NumberFormatException e) {
				return false;
			}
			if ((!getIsFahrenheit()) && (inputInteger > 100)) {
				view.displayWarning("C° range: 0°-100°");
				return false;
			} else if (getIsFahrenheit() && (inputInteger > 212)) {
				view.displayWarning("F° range: 0°-212°");
				return false;
			} else {
				return true;
			}
		}
	}
}
