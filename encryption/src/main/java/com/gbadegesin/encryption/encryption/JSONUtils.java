package com.gbadegesin.encryption.encryption;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gbadegesin.encryption.config.EncryptionServiceConfig;
import com.gbadegesin.encryption.exceptions.BadRequestException;
import com.gbadegesin.encryption.exceptions.GenericException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;


@Component
@RequiredArgsConstructor
public class JSONUtils {


    private final static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    private final static Logger log = LoggerFactory.getLogger(JSONUtils.class);

    private final EncryptionServiceConfig config;

//    private static String payloadEncKey;

//    public void setPayloadEncKey() {
//        JSONUtils.payloadEncKey = config.getPayloadEncKey();
//    }


    /**
     * Stringifies an object into a string
     * @param sourceObject: the object to stringify
     * @return the stringify object
     */
    public static <T> String stringifyObject(T sourceObject) {

        if (sourceObject.getClass() == String.class) {
            return (String) sourceObject;
        }

        try {
            return objectMapper.writeValueAsString(sourceObject);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GenericException(e.getLocalizedMessage(), "Couldn't convert object to JSON", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Converts stringify json object back to object
     * @param json: the json string
     * @param clazz: the object target class
     * @param <T>:  the class parameter
     * @return the converted object
     */
    public static <T> T toObject(String json, Class<T> clazz) {

        try {
            return objectMapper.readValue(json, clazz);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GenericException(e.getLocalizedMessage(), "Couldn't string to object payload", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Encrypts an object and returns the string value
     * @param object: the object to encrypt
     * @param <T>: the class type of the object
     * @return the encrypted string
     */
    public <T> String encryptObjectToString(T object) {

        if(object == null)
            return null;

        try {
            return RsaAesGcmStandard.encryptTextUsingAES(stringifyObject(object), config.getPayloadEncKey());
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GenericException(e.getLocalizedMessage(), "Couldn't encrypt payload",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String encryptString(String stringPayload) {

        try {
            return RsaAesGcmStandard.encryptTextUsingAES(stringPayload, config.getPayloadEncKey());
        }catch (Exception e){
            log.error("Error in encrypting object due to {}", e.getMessage());
            throw new GenericException(e.getLocalizedMessage(), "Couldn't encrypt  string payload", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Decrypts a string and returns the object value
     * @param objectString: the string to decrypt
     * @param <T>: the class type of the target object
     * @return the target object
     */
    public <T> T decryptStringToObject(String objectString, Class<T> targetObjectClass) {

        try {
            if (targetObjectClass == String.class) {
                return (T) RsaAesGcmStandard.decryptTextUsingAES(objectString, config.getPayloadEncKey());
            } else {
                return toObject(RsaAesGcmStandard.decryptTextUsingAES(objectString, config.getPayloadEncKey()), targetObjectClass);
            }
        } catch (Exception e) {
            log.info("Couldn't decrypt request payload");
            log.error(e.getMessage(), e);
            throw new GenericException(e.getLocalizedMessage(), "Couldn't decrypt payload",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String decryptStringToJson(String objectString) {

        try {
            return RsaAesGcmStandard.decryptTextUsingAES(objectString, config.getPayloadEncKey());
        } catch (Exception e) {
            log.info("Couldn't decrypt request payload because: {}", e.getLocalizedMessage());
            log.error(e.getMessage(), e);
            throw new GenericException(e.getLocalizedMessage(), "Couldn't decrypt payload",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String decryptString(String encStringPayload) {

        try {
            return RsaAesGcmStandard.decryptTextUsingAES(encStringPayload, config.getPayloadEncKey());
        } catch (Exception e) {
            log.error("Error in decrypting to object due to {}", e.getMessage());
            throw new GenericException(e.getLocalizedMessage(), "Couldn't decrypt payload",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public String generateSHA512(String password1) {//   password = unique ref+ amount + beneficiary account
//        log.info(":::::::::::::this param request :::::::::::" + password1);
        StringBuilder hexString;
        String hashkey = "(nmi#90$sSDF5cgy62372ll>";

        String password = hashkey + password1;
        try {
            if (password1==null){
                throw new BadRequestException("Password cannot be empty", HttpStatus.BAD_REQUEST);
            }
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte datum : byteData) {
                sb.append(Integer.toString((datum & 0xff) + 0x100, 16).substring(1));
            }
            hexString = new StringBuilder();
            for (byte byteDatum : byteData) {
                String hex = Integer.toHexString(0xff & byteDatum);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (Exception e){
            log.error("password encryption failed", e);
            throw new GenericException(e.getLocalizedMessage(), "Couldn't generate hash",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return hexString.toString();
    }


//    public static void main(String[] args) {
//
//        String enc = encryptObjectToString("Gbade");
//        String dec = decryptStringToObject(enc, String.class);
//        System.out.println(dec);
//    }

}