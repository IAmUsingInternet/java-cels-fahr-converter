/**
 * Temperature-unit converter based on given mockup-design
 * @author Philip L
 **/
package tempcalc.model;

import java.util.ArrayList;

import tempcalc.view.StatObserver;

public class TempModel implements TempModelInterface {

	private ArrayList<StatObserver> statObservers = new ArrayList<StatObserver>();
	private double cels;
	private double fahr;

	void celsToFahr(double c) {
		fahr = (c * 1.8 + 32.0);
	}

	void fahrToCels(double f) {
		cels = ((f - 32.0) * (5.0 / 9.0));
	}

	@Override
	public void initialize() {
		cels = 0.0;
		fahr = 32.0;
		notifyStatObservers();
	}

	@Override
	public double getCels() {
		return cels;
	}

	@Override
	public double getFahr() {
		return fahr;
	}

	@Override
	public void setCels(double c) {
		cels = c;
		celsToFahr(c);
		notifyStatObservers();
	}

	@Override
	public void setFahr(double f) {
		fahr = f;
		fahrToCels(f);
		notifyStatObservers();
	}

	@Override
	public void registerObserver(StatObserver o) {
		statObservers.add(o);
	}

	public void notifyStatObservers() {
		for (StatObserver observer : statObservers) {
			observer.updateStat();
		}
	}

	@Override
	public void removeObserver(StatObserver o) {
		int i = statObservers.indexOf(o);
		if (i >= 0) {
			statObservers.remove(i);
		}
	}
}
