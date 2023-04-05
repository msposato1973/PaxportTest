package com.gocity.demo.constant;

public interface Constants {
	
	
	public final static String  URL_BASE = "https://walg.paxcache.com/api/v2/flights/fixed?";
	public final static String  PARAM_DEPART = "departureDate";
	public final static String  PARAM_DEST = "destination";
	public final static String  PARAM_FARE = "fare";
	public final static String  PARAM_NUM_ADU = "numAdults";
	public final static String  PARAM_NUM_CH = "numChildren";
	public final static String  PARAM_NUM_INF = "numInfants";
	public final static String  PARAM_NUM_Y = "numYouths";
	public final static String  PARAM_ORI = "origin";
	
	public final static String FORBIDDEN = "Forbidden - Invalid or Missing API key";
	public final static String NOT_FOUND = "FLY not found for: ";
	public final static String ERROR = "Internal Server Error";
	public final static String BED_REQUEST = "Bad Request";
	
	public static final String API = "/api/v1";
    public static final String POST_FLIGHT_SEARC_API = "/flightSearch";
    public static final String GET_FLIGHT_SEARC_API = "/flightSearch";
    public static final String FLIGHT_SEARCH_WITH_PARMA = GET_FLIGHT_SEARC_API + "/{departureDate}/{destination}/{origin}";
    
    
    
    public static final String ERROR_NULL =  "Exception - FlyRequast is null";
    public static final String ERROR_AUTH =  "ExceptionAuthentication - basic Authentication is null";
    
    public static final String ERROR_INTER = "Internal Server Exception while getting exception";
    public static final String ERROR_NO_DATA_FOUND = "No Flight found it for origine, destination and date";

    
    
    public static final String CONTENT =  "Content-Type";
    public static final String CONTENT_VAL =  "application/json";
    public static final String ACC = "Accept";
    public static final String ACC_VAL = "*/*";
    public static final String ACCENC = "Accept-Encoding";
    public static final String ACCENC_VAL = "gzip, deflate, br";
    public static final String  CONN = "Connection";
    public static final String  CONN_VAL =  "keep-alive";
    	    
}
