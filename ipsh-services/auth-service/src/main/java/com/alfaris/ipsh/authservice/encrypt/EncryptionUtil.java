package com.alfaris.ipsh.authservice.encrypt;

/**
* Project Name : CIBMobile
* @author Interland
* Date Created   : 03-Feb-2020
* 
*/
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

public final class EncryptionUtil {
	public static String encryptPassword(String password) throws Exception {
		Encryption encryption = new EncryptionConfiguration();
		char[] tmp = (password == null) ? new char[0] : password.toCharArray();

		return encryption.encrypt(tmp);
	}

	public static String decryptPassword(String encryptedPsw) throws Exception {
		Encryption encryption = new EncryptionConfiguration();
		return encryption.decrypt(encryptedPsw);
	}

	public static String decryptSupersededPassword(String encryptedPsw) throws Exception {
		Encryption encryption = new EncryptionConfiguration(6);

		return encryption.decrypt(encryptedPsw);
	}

	public static String encrypt(String str) {
		String encrypt = new String(Base64.getEncoder().encode(str.getBytes()));
		int code;
		String result = "";
		for (int i = 0; i < encrypt.length(); i++) {
			code = Math.round((float) Math.random() * 8 + 1);
			// result += code + Integer.toHexString(((int) encrypt.charAt(i)) ^ code) + "-";
			result += code + Integer.toHexString(((int) encrypt.charAt(i)) ^ code);

		}
		// return result.substring(0, result.lastIndexOf("-"));
		return result;
	}

	public static String decrypt(String str) {
		// str = str.replace("-", "");
		String result = "";
		for (int i = 0; i < str.length(); i += 3) {
			String hex = str.substring(i + 1, i + 3);
			result += (char) (Integer.parseInt(hex, 16) ^ (Integer.parseInt(String.valueOf(str.charAt(i)))));

		}
		String decrypt = new String(Base64.getDecoder().decode(result));
		return decrypt;
	}

	public static void main(String args[]) throws Exception {
		System.out.println(EncryptionUtil.decryptPassword(("whQWK+y1HHUsq10+h31YAg==")));
		// System.out.println(EncryptionUtil.encryptPassword(("")));

		DateFormat sdf = new SimpleDateFormat("dd MMM HH:mm");
		// System.out.println(sdf.format(new Date()));

	}

}
