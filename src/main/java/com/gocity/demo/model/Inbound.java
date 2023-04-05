package com.gocity.demo.model;

import java.util.ArrayList;

public class Inbound {
	public boolean isFlightFlown;
    public boolean isAlreadyUpgraded;
    public Object message;
    public boolean isLongHaul;
    public boolean isTransatlantic;
    public boolean isNorthAmerican;
    public boolean isUk;
    public ArrayList<Flight> flights;
    public ArrayList<Object> discountedFareTypes;
}
