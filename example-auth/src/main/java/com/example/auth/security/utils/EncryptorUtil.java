package com.example.auth.security.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Component;

@Component
public class EncryptorUtil  implements IEncryptorUtil{

	@Value("${refresh-token.encode.secret}")
	private String secret;

	@Value("${refresh-token.encode.salt}")
	private String salt;
	
	
	@Override
	public String encode(String message) {
		BytesEncryptor encryptor  = Encryptors.standard(secret, salt);
		byte [] encrypted = encryptor.encrypt( message.getBytes() );	
		return Base64.getEncoder().encodeToString( encrypted );
	}

	@Override
	public String decode(String message) {
		BytesEncryptor encryptor  = Encryptors.standard( secret, salt );
		byte[] messageByte = Base64.getDecoder().decode( message );
		byte [] encrypted = encryptor.decrypt( messageByte );	
		return new String( encrypted, StandardCharsets.UTF_8);
	}

	@Override
	public Boolean matches(String message, String messageEncode) {
		String messageDecode = this.decode(messageEncode);
		return message.equals(messageDecode);
	}

}




