/**
 * Temperature-unit converter based on given mockup-design
 * @author Philip L
 **/
package tempcalc.main;

import tempcalc.controller.TempController;
import tempcalc.controller.TempControllerInterface;
import tempcalc.model.TempModel;
import tempcalc.model.TempModelInterface;

public class TempCalculator {
	public static void main(String[] args) {
		TempModelInterface model = new TempModel();
		TempControllerInterface controller = new TempController(model);
	}
}
