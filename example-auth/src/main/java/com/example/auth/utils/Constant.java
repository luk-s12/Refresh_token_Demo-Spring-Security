package com.example.auth.utils;

public class Constant {
	
	public static final String PATTER_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	public static final String URI_PATH = "uri=";

	// Message Exception Advice
	public static final String SIGN_IN_AGAIN 		 = "Vuelva a iniciar sesion.";
	public static final String EMPTY_REFRESH_TOKEN	 = "No posee un access token." 	   + SIGN_IN_AGAIN;
	public static final String INVALID_REFRESH_TOKEN = "El refresh token es corrupto." + SIGN_IN_AGAIN;
	public static final String EXPIRED_REFRESH_TOKEN = "El refresh token es corrupto." + SIGN_IN_AGAIN;
	public static final String FIELD_ERROR 			 = "Campos enviados invalidos. Vuelva a intentar mandarlo correctamente";
	public static final String ACCESS_DENIED 		 = "No se encuentra autorizado para este recurso";

	


	
	
	
	
	
}
