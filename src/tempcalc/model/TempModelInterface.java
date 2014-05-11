/**
 * Temperature-unit converter based on given mockup-design
 * @author Philip L
 **/

package tempcalc.model;

import tempcalc.view.StatObserver;

public interface TempModelInterface {
	void initialize();

	void setCels(double c);

	void setFahr(double f);

	double getCels();

	double getFahr();

	void registerObserver(StatObserver o);

	void removeObserver(StatObserver o);

}
