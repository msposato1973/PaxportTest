package com.gocity.demo.model;

import java.util.ArrayList;

public class Outbound {
	
	public boolean isFlightFlown;
    public boolean isAlreadyUpgraded;
    public String message;
    public boolean isLongHaul;
    public boolean isTransatlantic;
    public boolean isNorthAmerican;
    public boolean isUk;
    public ArrayList<Flight> flights;
    public ArrayList<String> discountedFareTypes;
    
}
