/**
 * Temperature-unit converter based on given mockup-design
 * @author Philip L
 **/

package tempcalc.controller;

public interface TempControllerInterface {

	void switchUnit();

	void convert();

	/**
	 * Handler for JSliders
	 * 
	 * @param value
	 *            temperature-value
	 * @param isfahrenheit
	 *            true for fahrSlider, false for celsSlider
	 */
	void sliderReact(int value, boolean isfahrenheit);

	boolean getIsFahrenheit();

	void updateView(double cels, double fahr);

	boolean checkInput(String text);

}
