/**
 * Temperature-unit converter based on given mockup-design
 * @author Philip L
 **/

package tempcalc.view;

public interface TempViewInterface {

	void changeSwitchButton(boolean b);

	String getInputText();

	void displayWarning(String string);

	void displayText(String string);

	void setFahrSliderValue(int fahr);

	void changeInputText(String s);

	void setCelsSliderValue(int cels);

	void createView();

}
