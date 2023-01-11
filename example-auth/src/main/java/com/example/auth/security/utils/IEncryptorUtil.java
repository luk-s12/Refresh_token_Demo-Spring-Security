package com.example.auth.security.utils;

public interface IEncryptorUtil {
	
	String encode(String message);
	String decode(String message);
	Boolean matches(String message, String messageEncode);
}
