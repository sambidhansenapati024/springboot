package com.alfaris.ipsh.authservice.encrypt;
/**
* Project Name : CIBMobile
* @author Interland
* Date Created   : 03-Feb-2020
* 
*/
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.Signature;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.crypto.Cipher;


//import org.springframework.security.web.csrf.CsrfToken;
//import org.springframework.security.web.csrf.DefaultCsrfToken;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityProviderUtil {
    private static final String kResourceBundle = "application";

    static Signature getSignature(String algName, String customjceProvider)
            throws Exception {
        if (!(isNullOrEmpty(customjceProvider))) {
            return Signature.getInstance(algName, customjceProvider);
        }

        return Signature.getInstance(algName);
    }

    static MessageDigest getMessageDigest(String algName,
            String customjceProvider) throws Exception {
        if (!(isNullOrEmpty(customjceProvider))) {
            return MessageDigest.getInstance(algName, customjceProvider);
        }

        return MessageDigest.getInstance(algName);
    }

    static Cipher getCipher(String algName, String customjceProvider)
            throws Exception {
        if (!(isNullOrEmpty(customjceProvider))) {
            return Cipher.getInstance(algName, customjceProvider);
        }

        return Cipher.getInstance(algName);
    }

    static KeyStore getKeyStoreInstance(String storeType,
            String customjceProvider) throws Exception {
        if (!(isNullOrEmpty(customjceProvider))) {
            return KeyStore.getInstance(storeType, customjceProvider);
        }

        return KeyStore.getInstance(storeType);
    }

    static String getProperty(String key) throws Exception {
        String value = null;
        try {
            ResourceBundle resourceBundle = ResourceBundle
                    .getBundle(kResourceBundle);

            value = resourceBundle.getString(key);
        } catch (MissingResourceException e) {
        }
        return value;
    }

    static MessageDigest getMessageDigest(String algName) throws Exception {
        String customjceProvider = getProperty("security.crypto.cipher.provider.class");

        return getMessageDigest(algName, customjceProvider);
    }

    static Cipher getCipher(String algName) throws Exception {
        String customjceProvider = getProperty("security.crypto.cipher.provider.class");

        return getCipher(algName, customjceProvider);
    }

    static Signature getSignature(String algName) throws Exception {
        String customjceProvider = getProperty("security.crypto.cipher.provider.class");

        return getSignature(algName, customjceProvider);
    }

    static KeyStore getKeyStoreInstance(String storeType) throws Exception {
        String customjceProvider = getProperty("security.crypto.cipher.provider.class");

        return getKeyStoreInstance(storeType, customjceProvider);
    }

    static boolean isNullOrEmpty(String theString) {
        if (theString == null) {
            return true;
        }
        return theString.equals("");
    }
    
    public static void generateToken(HttpServletRequest request, String tokenName) {
       // return null;////new DefaultCsrfToken("csrf", tokenName, UUID.randomUUID().toString());
    }
    
    public static String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String secureString = salt.toString();
        return secureString;

    }
}
