package com.jtd.recharge.connect.flow.rongman;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Aes128CBCUtil {

	public static String encrypt( String src, String key, String iv ) throws Exception {
		if( key == null || key.length( ) != 16 ) {
			throw new InvalidKeyException( "key is wrong" );
		}
		if( iv == null || iv.length( ) != 16 ) {
			throw new InvalidAlgorithmParameterException( "iv  is wrong" );
		}
		SecretKeySpec sKeySpec = new SecretKeySpec( key.getBytes( "UTF-8" ), "AES" );
		Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
		IvParameterSpec ivSpec = new IvParameterSpec( iv.getBytes( "UTF-8" ) );
		cipher.init( Cipher.ENCRYPT_MODE, sKeySpec, ivSpec );
		return Base64.encodeBase64String( cipher.doFinal( src.getBytes( "UTF-8" ) ) );
	}

	public static String decrypt( String src, String key, String iv ) throws Exception {
		if( key == null || key.length( ) != 16 ) {
			throw new InvalidKeyException( "key is wrong" );
		}
		if( iv == null || iv.length( ) != 16 ) {
			throw new InvalidAlgorithmParameterException( "iv  is wrong" );
		}
		SecretKeySpec sKeySpec = new SecretKeySpec( key.getBytes( "UTF-8" ), "AES" );
		Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
		IvParameterSpec ivSpec = new IvParameterSpec( iv.getBytes( "UTF-8" ) );
		cipher.init( Cipher.DECRYPT_MODE, sKeySpec, ivSpec );
		byte[] content = cipher.doFinal( Base64.decodeBase64( src.getBytes( "UTF-8" ) ) );
		return new String( content );
	}
}
