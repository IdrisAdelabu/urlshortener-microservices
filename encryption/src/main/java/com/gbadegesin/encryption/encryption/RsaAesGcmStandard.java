package com.gbadegesin.encryption.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;



public class RsaAesGcmStandard {

	private static final String RSA_ENCRYPT_ALGO = "RSA/ECB/PKCS1Padding"; // RSA Algorithm/Mode/Padding
	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding"; // AES Algorithm/Mode/Padding
	private static final int TAG_LENGTH_BIT = 128; // Length of authentication tag
	private static final int IV_LENGTH_BYTE = 12; // Length of initialization vector
	private static final Charset UTF_8 = StandardCharsets.UTF_8;


	public static String getSecretAESKeyAsString() throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(256); // The AES key size in number of bits
		SecretKey secKey = generator.generateKey();
		return Base64.getEncoder().encodeToString(secKey.getEncoded());
	}


	public static String encryptAESKey(String plainAESKey, String publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA_ENCRYPT_ALGO);
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		return Base64.getEncoder().encodeToString(cipher.doFinal(Base64.getDecoder().decode(plainAESKey)));
	}


	public static String decryptAESKey(String data, String base64PrivateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA_ENCRYPT_ALGO);
		cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(base64PrivateKey));
		return Base64.getEncoder().encodeToString(cipher.doFinal(Base64.getDecoder().decode(data)));
	}


	public static String encryptTextUsingAES(String plainText, String aesKeyString) throws Exception {
		System.out.println("PayloadEncKey: " + aesKeyString);
		byte[] decodedKey = Base64.getDecoder().decode(aesKeyString);
		byte[] iv = getRandomNonce(IV_LENGTH_BYTE);

		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		Cipher aesCipher = Cipher.getInstance(ENCRYPT_ALGO);
		aesCipher.init(Cipher.ENCRYPT_MODE, originalKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

		byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes(UTF_8));
		// We prefix the IV to the encrypted text, because we need the same IV for Decryption
		byte[] cipherTextWithIv = ByteBuffer.allocate(iv.length + byteCipherText.length).put(iv).put(byteCipherText)
				.array();

		return Base64.getEncoder().encodeToString(cipherTextWithIv);
	}





	public static String decryptTextUsingAES(String encryptedText, String aesKeyString) throws Exception {
		byte[] decodedKey = Base64.getDecoder().decode(aesKeyString);
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		byte[] decode = Base64.getDecoder().decode(encryptedText.getBytes(UTF_8));
		ByteBuffer bb = ByteBuffer.wrap(decode);
		byte[] iv = new byte[IV_LENGTH_BYTE];
		bb.get(iv);
		byte[] cipherText = new byte[bb.remaining()];
		bb.get(cipherText);

		Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
		cipher.init(Cipher.DECRYPT_MODE, originalKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
		byte[] plainText = cipher.doFinal(cipherText);

		return new String(plainText, UTF_8);
	}

	protected static PublicKey getPublicKey(String base64PublicKey) {
		PublicKey publicKey = null;
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return publicKey;
	}

	protected static PrivateKey getPrivateKey(String base64PrivateKey) {
		PrivateKey privateKey = null;
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateKey;
	}

	protected static byte[] getRandomNonce(int len) {
		byte[] nonce = new byte[len];
		new SecureRandom().nextBytes(nonce);
		return nonce;
	}



	public static void main(String [] args)throws Exception {

		//String key = getSecretAESKeyAsString();
		String key = "47gbrJFGy0nSGWFv/HxgFLYfS7vIDgBzTmbAsiUM/wo=";
		String me = "{\n" +
				"    \"userId\": \"iadelabu@ecobank.com\"\n" +
				"}";
		String enc = encryptTextUsingAES("gbade.com/this-is-a-splendid-test", key);

//		System.out.println(key);
//		System.out.println(enc);
//		System.out.println(decryptTextUsingAES(enc, key));
		System.out.println(enc);
		System.out.println();
		System.out.println(decryptTextUsingAES(enc, key));
	}
}
