package lib.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author harvi
 * @modified by Parth Nayak
 */

public class Continent extends Land{

	private int controlValue;
	
	private String color;
	
	private List<Country> countriesList = new ArrayList<>();

	public Continent(String continentName, int controlValue, String color) {
		super(continentName);
		this.controlValue = controlValue;
		this.color = color;
	}

	public int getControlValue() {
		return controlValue;
	}

	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void addCountry(Country c) {
		countriesList.add(c);
	}
	
	public List<Country> getCountriesList() {
		return countriesList;
	}

	
}
