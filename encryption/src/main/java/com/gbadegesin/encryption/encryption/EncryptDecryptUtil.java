package com.gbadegesin.encryption.encryption;//package com.ecobank.EdcAccountService.utils.encryption;
//
//
//import com.ecobank.EdcAccountService.utils.ResponseCodes;
//import com.ecobank.EdcAccountService.utils.JSONUtils;
//
//import com.ecobank.EdcAccountService.utils.exceptions.EdcException;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.GCMParameterSpec;
//import java.nio.ByteBuffer;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//
//
//
//@Slf4j
//public class EncryptDecryptUtil {
//
//    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
//    private static final int TAG_LENGTH_BIT = 128;
//    private static final int IV_LENGTH_BYTE = 12;
//    private static final int SALT_LENGTH_BYTE = 16;
//    private static final Charset UTF_8 = StandardCharsets.UTF_8;
//    private static final AesUtil aesUtil  =  new AesUtil();
//
//    // @Value("${encrypt.key}")
//    //private String password;
//
//    static String password="eRcNrtt=nR*42xxvE&cqk&GUSB85zkKbqeUWm2W&QGBRbGFzZbR?dFKC6sJn2ACN&Aj&?L#f3@6fprRn-=YHBPyrPa9HH+2ZjBySRpBhEV?AR9dZ+#%aPD4W#LP!-JC?RQP%$uNh?GfSyM5AvK?kcKZDa@h^8ZcWp8jLF-MkHcF_uuutH?45Y4whnbPn@gj%5_CtYp$vtBcdyP8g+sq?H5Q=nvAZUh+$qQ6Y2HN-#7fu*@%t^bbwu9F6xRS8VmXD";
//
//
//    private static String encrypt(String text)  {
//
//        try {
//            log.info("Text to Encrypt : {}", text);
//            byte[] pText = text.getBytes(UTF_8);
//            byte[] salt = aesUtil.getSaltRandomByte(SALT_LENGTH_BYTE);
//            byte[] iv = aesUtil.getSaltRandomByte(IV_LENGTH_BYTE);
//            SecretKey aesKeyFromPassword = aesUtil.getAESKeyFromPassword(password.toCharArray(), salt);
//            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
//            cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
//            byte[] cipherText = cipher.doFinal(pText);
//            byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
//                    .put(iv)
//                    .put(salt)
//                    .put(cipherText)
//                    .array();
//            return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
//        }
//        catch(Exception e){
//            log.error(e.getMessage(), e);
//            throw new EdcException.GenericException(ResponseCodes.PROCESS_ERROR, "couldn't encrypt param", null);
//        }
//    }
//
//    private static String decrypt(String cText) {
//
//        try{
//
//            byte[] decode = Base64.getDecoder().decode(cText.getBytes(UTF_8));
//            ByteBuffer bb = ByteBuffer.wrap(decode);
//            byte[] iv = new byte[IV_LENGTH_BYTE];
//            bb.get(iv);
//            byte[] salt = new byte[SALT_LENGTH_BYTE];
//            bb.get(salt);
//            byte[] cipherText = new byte[bb.remaining()];
//            bb.get(cipherText);
//            SecretKey aesKeyFromPassword = aesUtil.getAESKeyFromPassword(password.toCharArray(), salt);
//            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
//            cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
//            byte[] plainText = cipher.doFinal(cipherText);
//            log.info("decrypted Text {}",new String(plainText, UTF_8));
//            return new String(plainText, UTF_8);
//        }
//        catch(Exception e){
//            log.error(e.getMessage(), e);
//            throw new EdcException.GenericException(ResponseCodes.PROCESS_ERROR, "couldn't encrypt param", null);
//        }
//    }
//
//
//    /**
//     * Encrypts an object and returns the string value
//     * @param object: the object to encrypt
//     * @param <T>: the class type of the object
//     * @return the encrypted string
//     */
//    public static <T> String encryptObjectToString(T object)  {
//
//        if(object == null)
//            return null;
//
//        return encrypt(JSONUtils.stringifyObject(object));
//    }
//
//
//
//    /**
//     * Decrypts a string and returns the object value
//     * @param objectString: the string to decrypt
//     * @param <T>: the class type of the target object
//     * @return the target object
//     */
//    public static <T> T decryptStringToObject(String objectString, Class<T> targetObjectClass) {
//
//        try {
//            return JSONUtils.toObject(decrypt(objectString), targetObjectClass);
//        }catch (Exception e){
//            log.info("Couldn't decrypt request payload");
//            log.error(e.getMessage(), e);
//            throw new EdcException.GenericException(ResponseCodes.PROCESS_ERROR, "Error occurred while decrypting request payload", null);
//        }
//    }
//
//
//}
