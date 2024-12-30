package com.alfaris.ipsh.security.encrypt;
/**
* Project Name : CIBMobile
* @author Interland
* Date Created   : 03-Feb-2020
* 
*/
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import com.alfaris.b2b.service.AccountTypeActionServiceImpl;
//import com.alfaris.b2b.util.CryptoConfig;
//import com.alfaris.b2b.util.Encryption;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
import java.util.Base64;

abstract class Encryption {
	private static final Logger logger = Logger.getLogger(Encryption.class.getName());

    static final String kDEFAULTAPPKEY = "DEFAULT";
    static final String kDEFAULTAPPKEYPREFIX = "C.";
    private static volatile Map<String, CryptoConfig> cfgMap = new ConcurrentHashMap();
    private static final String kUTF8 = "UTF8";
    private static final String kEmpty = "";
    private static final String kPBEWithMD5AndDES = "PBEWithMD5AndDES";
    private static final String kEncyrptionModeNotSupportedMessage = "The encryption mode is not supported.";
    private static final String kDecryptionModeNotSupportedMessage = "The decryption mode is not supported.";
    private static final String kMD5 = "MD5";
    private static final String kSHA = "SHA";
    private static final int kIterationCount = 19;
    private int encryptionMode;
    private Cipher ecipher;
    private Cipher dcipher;
    private Cipher encrCipher;
    private Cipher decrCipher;
    private String applicationKey;
    private final byte[] salt1;

    Encryption() {
        this.encryptionMode = 1;

        this.salt1 = new byte[] { -87, -101, -56, 50, 86, 53, -29, 3 };
    }

    int getEncryptionMode() {
        return this.encryptionMode;
    }

    protected void setConfiguration(String applicationKey) {
        setApplicationKey(applicationKey);

        CryptoConfig cfg = (CryptoConfig) cfgMap.get(applicationKey);

        if (cfg != null) {
            return;
        }
        if (cfg == null) {
            String key = applicationKey;
            if (applicationKey.equals("DEFAULT")) {
                key = "";
            }

            synchronized (cfgMap) {
                if (cfgMap.get(applicationKey) == null) {
                    cfg = new CryptoConfig(key);

                    if (!(cfg.isUseStatic())) {
                        try {
                            cfg.getCipherKey(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    cfgMap.put(applicationKey, cfg);
                }
            }
        }
    }

    private void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }

    void setEncryptionMode(int encryptMode) {
        this.encryptionMode = encryptMode;
    }

    private byte[] getSalt() {
        return this.salt1;
    }

    String encrypt(char[] data) throws Exception {
        switch (this.encryptionMode) {
        case 4:
            return cipher(data);
        case 6:
            return cipher(data, true);
        case 5:
            return digest(data);
        case 7:
            return digest(data, true);
        case 1:
            return hashEncrypt(data, "SHA");
        case 3:
            return encryptPBE(data);
        case 2:
            return hashEncrypt(data, "MD5");
        }

        Exception e = new Exception("The encryption mode is not supported.");

        throw e;
    }

    String decrypt(String data) throws Exception {
        if (this.encryptionMode == 3)
            return new String(decryptPBE(data));
        if (this.encryptionMode == 4)
            return new String(deCipher(data));
        if (this.encryptionMode == 6) {
            return new String(deCipher(data, true));
        }

        Exception se = new Exception("The decryption mode is not supported.");

        throw se;
    }

    private String hashEncrypt(char[] data, String algorithm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(new String(data).getBytes("UTF8"));
        byte[] encrypted = md.digest();
        return encode(encrypted);
    }

    private String encryptPBE(char[] data) throws Exception {
        createEKey();

        byte[] utf8 = new String(data).getBytes("UTF8");

        byte[] enc = this.ecipher.doFinal(utf8);

        return encode(enc);
    }

    private char[] decryptPBE(String data) throws Exception {
        createDKey();

        byte[] dec = decode(data);

        byte[] utf8 = this.dcipher.doFinal(dec);

        return new String(utf8, "UTF8").toCharArray();
    }

    private void createEKey() throws Exception {
        if (this.ecipher != null) {
            return;
        }

        KeySpec keySpec = new PBEKeySpec("This a Pass Phrase".toCharArray());
        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
                .generateSecret(keySpec);

        this.ecipher = Cipher.getInstance("PBEWithMD5AndDES");

        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(getSalt(), 19);

        this.ecipher.init(1, key, paramSpec);
    }

    private void createDKey() throws Exception {
        if (this.dcipher != null) {
            return;
        }

        KeySpec keySpec = new PBEKeySpec("This a Pass Phrase".toCharArray());
        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
                .generateSecret(keySpec);

        this.dcipher = Cipher.getInstance("PBEWithMD5AndDES");

        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(getSalt(), 19);

        this.dcipher.init(2, key, paramSpec);
    }

    private String cipher(char[] text) throws Exception {
        return cipher(text, false);
    }

    private String cipher(char[] text, boolean isSuperseded) throws Exception {
        CryptoConfig cfg = (CryptoConfig) cfgMap.get(this.applicationKey);

        if ((cfg.isUseStatic()) && (!(isSuperseded))) {
            return encryptPBE(text);
        }

        if (this.encrCipher == null) {
            this.encrCipher = SecurityProviderUtil.getCipher(cfg
                    .getCipherAlgorithm(isSuperseded));
        }

        byte[] key = cfg.getCipherKey(isSuperseded);

        this.encrCipher.init(1,
                new SecretKeySpec(key, cfg.getCipherAlgorithm(isSuperseded)));

        byte[] cipher = this.encrCipher.doFinal(new String(text)
                .getBytes("UTF-8"));

        byte x = 0;
        Arrays.fill(key, x);
        return encode(cipher);
    }

    private String encode(byte[] cipher) {
//        return new BASE64Encoder().encode(cipher);
        return Base64.getEncoder().encodeToString(cipher);
    }

    private byte[] decode(String cipher) throws Exception {
//        return new BASE64Decoder().decodeBuffer(cipher);
    	return Base64.getDecoder().decode(cipher);
    }

    private char[] deCipher(String text) throws Exception {
        return deCipher(text, false);
    }

    private char[] deCipher(String text, boolean isSuperseded) throws Exception {
        CryptoConfig cfg = (CryptoConfig) cfgMap.get(this.applicationKey);

        if ((cfg.isUseStatic()) && (!(isSuperseded))) {
            return decryptPBE(text);
        }

        if (this.decrCipher == null) {
            if ((isSuperseded)
                    && ("PBEWithMD5AndDES".equals(cfg
                            .getCipherAlgorithm(isSuperseded)))) {
                return decryptPBE(text);
            }

            this.decrCipher = SecurityProviderUtil.getCipher(cfg
                    .getCipherAlgorithm(isSuperseded));
        }

        if ((isSuperseded)
                && ("PBEWithMD5AndDES".equals(cfg
                        .getCipherAlgorithm(isSuperseded)))) {
            return decryptPBE(text);
        }

        byte[] key = cfg.getCipherKey(isSuperseded);

        this.decrCipher.init(2,
                new SecretKeySpec(key, cfg.getCipherAlgorithm(isSuperseded)));

        byte[] decodeBytes = decode(text);
        byte[] utf8 = this.decrCipher.doFinal(decodeBytes);

        byte x = 0;
        Arrays.fill(key, x);

        return new String(utf8, "UTF-8").toCharArray();
    }

    private String digest(char[] text) throws Exception {
        return digest(text, false);
    }

    private String digest(char[] text, boolean isSuperseded) throws Exception {
        CryptoConfig cfg = (CryptoConfig) cfgMap.get(this.applicationKey);

        if (cfg.isUseStatic()) {
            return hashEncrypt(text, "SHA");
        }

        MessageDigest md = SecurityProviderUtil.getMessageDigest(cfg
                .getMessageDigestAlgorithm(isSuperseded));

        md.update(new String(text).getBytes("UTF8"));

        byte[] salt = cfg.getMessageDigestSalt(isSuperseded);

        if (salt != null) {
            salt = String.valueOf(
                    deCipher(new String(salt, Charset.defaultCharset())))
                    .getBytes("UTF-8");

            md.update(salt);
            int iterationCount = cfg
                    .getMessageDigestIterationsCount(isSuperseded);

            for (int i = 0; i < iterationCount; ++i) {
                md.update(md.digest());
            }
        }
        byte[] encrypted = md.digest();
        return encode(encrypted);
    }

    public static boolean isNullOrEmpty(String theString) {
        if (theString == null) {
            return true;
        }
        return theString.equals("");
    }

    private static final class CryptoConfig implements Serializable {
        private static final String kPropertyPrefix = "curam.security.crypto";
        private static final String kCipherAlgorithm = "cipher.algorithm";
        private static final String kPropertyPrefix1 = "superseded";
        private static final String kPawd = "password";
        private static final String kCryptoFileLocation = "curam.security.crypto.file";
        private static final String kCryptoFileName = "CryptoConfig";
        private static final String kDot = ".";
        private static final String kEmpty = "";
        private static final String kKeyPass = "cipher.keystore.secretkey.keypass";
        private static final String kKeyAlias = "cipher.keystore.seckey.alias";
        private static final String kDefaultAlias = "CuramSystemPasswordKey";
        private static final String kDefaultSupersededAlias = "CuramSystemPasswordSupersededKey";
        private static final String kKeyFileProperty = "cipher.key.location";
        private static final String kKeyStoreFileProperty = "cipher.keystore.location";
        private static final String kJCEKS = "jceks";
        private static final String kDigestSalt = "digest.salt.location";
        private final ConcurrentHashMap<String, String> properties = new ConcurrentHashMap();
        private KeyStore kStore;
        private KeyStore supersededKStore;
        private boolean useStatic;
        private boolean enableDebug;

        CryptoConfig() {
            init("");
        }

        CryptoConfig(String applicationKey) {
            init(applicationKey);
        }

        private String getCryptoConfigName(String applicationKey) {
            return new StringBuilder()
                    .append("CryptoConfig")
                    .append((Encryption.isNullOrEmpty(applicationKey)) ? applicationKey
                            : new StringBuilder().append("_")
                                    .append(applicationKey).toString())
                    .append(".properties").toString();
        }

        private void init(String applicationKey) {
            try {
                if (this.properties.isEmpty()) {
                    InputStream inputStream = ClassLoader
                            .getSystemClassLoader().getResourceAsStream(
                                    getCryptoConfigName(applicationKey));

                    if (inputStream == null) {
                        inputStream = super
                                .getClass()
                                .getClassLoader()
                                .getResourceAsStream(
                                        getCryptoConfigName(applicationKey));
                    }

                    if (inputStream == null) {
                        logger.info("resource is empty");
                    }

                    if (inputStream == null) {
                        this.useStatic = true;
                        return;
                    }

                    Properties p = new Properties();
                    p.load(inputStream);

                    Set<String> keys = p.stringPropertyNames();

                    for (String s : keys) {
                        this.properties.put(s, p.getProperty(s));
                    }

                    String debugEnable = getProperty("enableDebug", false);

                    this.enableDebug = ((!(Encryption
                            .isNullOrEmpty(debugEnable))) ? Boolean.valueOf(
                            debugEnable).booleanValue() : false);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace(System.out);
                this.useStatic = true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public boolean isUseStatic() {
            return this.useStatic;
        }

        public boolean isEnableDebug() {
            return this.enableDebug;
        }

        String getCipherAlgorithm(boolean isSuperseded) throws Exception {
            if (isSuperseded) {
                return getCipherAlgorithmSuperseded();
            }
            return getProperty("cipher.algorithm", true);
        }

        private String getCipherAlgorithmSuperseded() throws Exception {
            return getProperty("superseded.cipher.algorithm", true);
        }

        byte[] getCipherKey(boolean isSuperseded) throws Exception {
            String supersededPrefix = "";
            if (isSuperseded) {
                supersededPrefix = "superseded.";
            }

            String keyFile = getProperty(
                    new StringBuilder().append(supersededPrefix)
                            .append("cipher.key.location").toString(), false);

            String keyStoreFile = getProperty(
                    new StringBuilder().append(supersededPrefix)
                            .append("cipher.keystore.location").toString(),
                    false);

            if ((Encryption.isNullOrEmpty(keyStoreFile))
                    && (Encryption.isNullOrEmpty(keyFile))) {
                throw new RuntimeException(new StringBuilder()
                        .append("Crypto Config Error :Properties Not Found:")
                        .append(expandProperty("cipher.keystore.location"))
                        .append(" or ")
                        .append(expandProperty("cipher.key.location"))
                        .toString());
            }

            if ((!(Encryption.isNullOrEmpty(keyStoreFile)))
                    && (!(Encryption.isNullOrEmpty(keyFile)))) {
                throw new RuntimeException(
                        new StringBuilder()
                                .append("Crypto Config Error :Ambiguous Properties Found:")
                                .append(expandProperty("cipher.keystore.location"))
                                .append(", ")
                                .append(expandProperty("cipher.key.location"))
                                .toString());
            }

            byte[] content = null;
            if (!(Encryption.isNullOrEmpty(keyFile))) {
                File cryptoConfig = new File(keyFile);
                BufferedInputStream bis = null;
                int len = 1024;
                if (cryptoConfig.exists()) {
                    bis = new BufferedInputStream(new FileInputStream(
                            cryptoConfig));

                    int fLength = (int) cryptoConfig.length();
                    if (fLength < len)
                        len = fLength;
                } else {
                    InputStream is = super.getClass().getClassLoader()
                            .getResourceAsStream(keyFile);

                    if (is == null) {
                        throw new MissingResourceException(new StringBuilder()
                                .append(keyFile).append(" Not Found")
                                .toString(), keyFile, "");
                    }

                    bis = new BufferedInputStream(is);
                }

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                byte[] buffer = new byte[len];
                try {
                    int b = bis.read(buffer);
                    while (b != -1) {
                        bytes.write(buffer, 0, b);
                        b = bis.read(buffer);
                    }
                    bytes.close();
                } catch (IOException e) {
                    throw new MissingResourceException(new StringBuilder()
                            .append(keyFile).append(" Not Found").toString(),
                            keyFile, "");
                }

                bis.close();
                content = bytes.toByteArray();
            } else if (!(Encryption.isNullOrEmpty(keyStoreFile))) {
                content = getKeyFromKeyStore(keyStoreFile, supersededPrefix);
            }

            return content;
        }

        private byte[] getKeyFromKeyStore(String keyStoreFile, String keyPrefix)
                throws Exception {
            byte[] content = null;

            KeyStore kyStore = (Encryption.isNullOrEmpty(keyPrefix)) ? this.kStore
                    : this.supersededKStore;

            String keyAlias = getKeyAlias(keyPrefix);

            String keyPass = getKeyPass(keyPrefix);

            String storePass = getStorePass(keyPrefix);

            if (Encryption.isNullOrEmpty(keyPass)) {
                keyPass = storePass;
            }
            if (kyStore == null) {
                File storeFile = new File(keyStoreFile);

                kyStore = KeyStore.getInstance("jceks");

                if (storeFile.exists()) {
                    kyStore.load(new FileInputStream(storeFile),
                            storePass.toCharArray());
                } else {
                    InputStream is = null;

                    String defaultVendor = (this.properties.get("jvm.vendor") == null) ? "sun"
                            : (String) this.properties.get("jvm.vendor");

                    String jvmVendor = (System.getProperty("java.vm.vendor")
                            .toLowerCase().contains("ibm")) ? "ibm"
                            : defaultVendor;

                    if (is == null) {
                        is = ClassLoader.getSystemClassLoader()
                                .getResourceAsStream(
                                        new StringBuilder().append(jvmVendor)
                                                .append("/")
                                                .append(keyStoreFile)
                                                .toString());
                    }

                    if (is == null) {
                        is = ClassLoader.getSystemClassLoader()
                                .getResourceAsStream(
                                        new StringBuilder().append("/")
                                                .append(jvmVendor).append("/")
                                                .append(keyStoreFile)
                                                .toString());
                    }

                    if (is == null) {
                        is = super
                                .getClass()
                                .getClassLoader()
                                .getResourceAsStream(
                                        new StringBuilder().append(jvmVendor)
                                                .append("/")
                                                .append(keyStoreFile)
                                                .toString());

                        if (is == null) {
                            is = super
                                    .getClass()
                                    .getClassLoader()
                                    .getResourceAsStream(
                                            new StringBuilder().append("/")
                                                    .append(jvmVendor)
                                                    .append("/")
                                                    .append(keyStoreFile)
                                                    .toString());
                        }

                    }

                    if (is == null) {
                        URL url = super.getClass().getClassLoader()
                                .getResource(getCryptoConfigName(""));

                        if (url != null) {
                            String fileUrl = url.getFile();

                            if (fileUrl.startsWith("file:")) {
                                fileUrl = fileUrl.substring(6);
                            }
                            File crypFile = new File(fileUrl);

                            if (!(url.getPath().contains(".jar"))) {
                                try {
                                    is = new FileInputStream(new File(
                                            new StringBuilder()
                                                    .append(crypFile
                                                            .getParentFile()
                                                            .getParent())
                                                    .append(File.separator)
                                                    .append(jvmVendor)
                                                    .append(File.separator)
                                                    .append(keyStoreFile)
                                                    .toString()));
                                } catch (FileNotFoundException e) {
                                }

                            }

                            if (is == null) {
                                try {
                                    is = new FileInputStream(new File(
                                            new StringBuilder()
                                                    .append(crypFile
                                                            .getParentFile()
                                                            .getParent())
                                                    .append(File.separator)
                                                    .append(keyStoreFile)
                                                    .toString()));
                                } catch (FileNotFoundException e) {
                                }

                            }

                        }

                    }

                    if (is == null) {
                        is = super.getClass().getClassLoader()
                                .getResourceAsStream(keyStoreFile);
                    }

                    if (is == null) {
                        throw new RuntimeException("Missing keystore");
                    }
                    kyStore.load(is, storePass.toCharArray());
                }

            }

            KeyStore.SecretKeyEntry keyEntry = (KeyStore.SecretKeyEntry) kyStore
                    .getEntry(
                            keyAlias,
                            new KeyStore.PasswordProtection(keyPass
                                    .toCharArray()));

            if (keyEntry == null) {
                throw new RuntimeException(new StringBuilder()
                        .append("Encryption Error : key alias not found: ")
                        .append(keyAlias).toString());
            }

            content = keyEntry.getSecretKey().getEncoded();
            if (!(getCipherAlgorithm(!(Encryption.isNullOrEmpty(keyPrefix)))
                    .equalsIgnoreCase(keyEntry.getSecretKey().getAlgorithm()))) {
                System.out
                        .println(new StringBuilder()
                                .append(getCipherAlgorithm(!(Encryption
                                        .isNullOrEmpty(keyPrefix))))
                                .append(" KeyStore Entry and Configuration not matching")
                                .append("for algorithm")
                                .append(keyEntry.getSecretKey().getAlgorithm())
                                .toString());
            }

            if (Encryption.isNullOrEmpty(keyPrefix))
                this.kStore = kyStore;
            else {
                this.supersededKStore = kyStore;
            }

            return content;
        }

        private String getKeyPass(String keyPrefix) throws Exception {
            String keyPass = getProperty(new StringBuilder().append(keyPrefix)
                    .append("cipher.keystore.secretkey.keypass").toString(),
                    false);

            return keyPass;
        }

        private String getKeyAlias(String keyPrefix) throws Exception {
            String keyAlias = getProperty(new StringBuilder().append(keyPrefix)
                    .append("cipher.keystore.seckey.alias").toString(), false);

            if (Encryption.isNullOrEmpty(keyAlias)) {
                keyAlias = "ApplicationSystemPasswordKey";
                if (!(Encryption.isNullOrEmpty(keyPrefix))) {
                    keyAlias = "ApplicationSystemPasswordSupersededKey";
                }
            }
            return keyAlias;
        }

        private String getStoreType(File storeFile, String keyPrefix)
                throws Exception {
            String storeType = getProperty(new StringBuilder()
                    .append(keyPrefix).append("keystore.type").toString(),
                    false);

            if (Encryption.isNullOrEmpty(storeType)) {
                storeType = storeFile.getName().substring(
                        storeFile.getName().lastIndexOf(".") + 1);
            }

            if (Encryption.isNullOrEmpty(storeType)) {
                throw new RuntimeException(new StringBuilder()
                        .append("Missing property:")
                        .append(expandProperty("cipher.keystore.type"))
                        .toString());
            }

            return storeType;
        }

        private String getStorePass(String keyPrefix) throws Exception {
            String storePass = getProperty(new StringBuilder()
                    .append(keyPrefix).append("cipher.keystore.storepass")
                    .toString(), false);

            if (Encryption.isNullOrEmpty(storePass)) {
                storePass = "password";
            }
            return storePass;
        }

        private void log(Throwable e) {
            if (isEnableDebug())
                e.printStackTrace(System.out);
            else
                logger.info(new StringBuilder().append("Error: ")
                        .append(e.getMessage()).toString());
        }

        private String getProperty(String key, boolean errorMode)
                throws Exception {
            try {
                if (this.properties == null) {
                    throw new RuntimeException("Missing Bundle: CryptoConfig");
                }

                String retValue = (String) this.properties
                        .get(expandProperty(key));

                if (retValue == null) {
                    if (errorMode) {
                        throw new MissingResourceException("", "CryptoConfig",
                                expandProperty(key));
                    }

                    return "";
                }

                return retValue;
            } catch (MissingResourceException e) {
                if (errorMode) {
                    throw e;
                }
            }
            return "";
        }

        int getMessageDigestIterationsCount(boolean isSuperseded)
                throws Exception {
            int count = 0;
            String iterationStr = (isSuperseded) ? getProperty(
                    "superseded.digest.iterations", false) : getProperty(
                    "digest.iterations", false);

            if (!(Encryption.isNullOrEmpty(iterationStr))) {
                count = Integer.parseInt(iterationStr);
            }

            return count;
        }

        String getMessageDigestAlgorithm(boolean isSuperseded) throws Exception {
            if (isSuperseded) {
                return getMessageDigestAlgorithmSuperseded();
            }
            return getProperty("digest.algorithm", true);
        }

        byte[] getMessageDigestSalt(boolean isSuperseded) throws Exception {
            String saltLocation = (isSuperseded) ? getProperty(
                    "superseded.digest.salt.location", false) : getProperty(
                    "digest.salt.location", false);

            File saltFile = new File(saltLocation);
            if (saltFile.exists()) {
                byte[] salt = new byte[(int) saltFile.length()];
                FileInputStream fis = new FileInputStream(saltFile);
                fis.read(salt);
                fis.close();
                return salt;
            }

            if (!(Encryption.isNullOrEmpty(saltLocation))) {
                InputStream is = super.getClass().getClassLoader()
                        .getResourceAsStream(saltLocation);

                if (is == null) {
                    throw new RuntimeException(
                            "digest.salt.location Not Exists");
                }

                BufferedInputStream bis = new BufferedInputStream(is);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                byte[] buffer = new byte[512];
                try {
                    int b = bis.read(buffer);
                    while (b != -1) {
                        bytes.write(buffer, 0, b);
                        b = bis.read(buffer);
                    }
                } catch (IOException e) {
                    throw new MissingResourceException(new StringBuilder()
                            .append(saltLocation).append(" Not Found")
                            .toString(), saltLocation, "");
                }

                bis.close();
                return bytes.toByteArray();
            }
            return null;
        }

        String getMessageDigestAlgorithmSuperseded() throws Exception {
            return getProperty("superseded.digest.algorithm", true);
        }

        private String expandProperty(String suffix) {
            return new StringBuilder().append("application.security.crypto.")
                    .append(suffix).toString();
        }

        static enum ConfigVersion {
            CURRENT, SUPERSEDED;
        }
    }

    public static abstract interface EncryptionMode {
        public static final int kSHA = 1;
        public static final int kMD5 = 2;
        public static final int kPBE = 3;
        public static final int kCIPHER = 4;
        public static final int kDIGEST = 5;
        public static final int kCIPHER_SUPERSEDED = 6;
        public static final int kDIGEST_SUPERSEDED = 7;
    }
}