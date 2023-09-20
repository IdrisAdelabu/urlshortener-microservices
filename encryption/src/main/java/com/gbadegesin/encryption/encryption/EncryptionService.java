package com.gbadegesin.encryption.encryption;//package com.ecobank.EdcAccountService.utils.encryption;
//
//import com.ecobank.EdcAccountService.utils.Response;
//import com.ecobank.EdcAccountService.utils.Credentials;
//import com.ecobank.EdcAccountService.utils.exceptions.BadHeaderValuesException;
//import com.ecobank.EdcAccountService.utils.exceptions.MissingHeaderException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Optional;
//
//@Service
//public class EncryptionService {
//    private static Logger log = LoggerFactory.getLogger(EncryptionService.class);
//
//
//    /*@Autowired
//     RsaAesGcmStandard RsaAesGcmStandard;*/
//
//    @Autowired
//     Environment environment;
//    private final static ObjectMapper objectMapper = new ObjectMapper();
//
//    @Value("${client.id}")
//    private String clientid;
//    @Value("${client.secret}")
//    private String clientsecret;
//    @Value("${client.sourcecode}")
//    private String sourcecode;
//
//
//
//
//
//
//
//    public  <T> String encrypt (T data, String sourceCode) throws BadHeaderValuesException {
//        log.info("source code {} :", sourceCode);
//        log.info("data    {}: ",data);
//        String aeskey = getAeskey(sourceCode);
//
////        log.info("aeskey    {}: ",aeskey);
//        String encryptedValue = null;
//        try {
//            String res = objectMapper.writeValueAsString(data);
//            encryptedValue = RsaAesGcmStandard.encryptTextUsingAES(res, aeskey);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//        log.info("encrypted value : {}", encryptedValue);
//        return encryptedValue;
//    }
//
//
//
//    public <T> T decrypt(String data, Class<T> elementClass, String sourceCode) throws BadHeaderValuesException {
//    	String aeskey = getAeskey(sourceCode);
//
//
//        T decryptedValue = null;
//        try {
//            String decryptedData = RsaAesGcmStandard.decryptTextUsingAES(data, aeskey);
//            decryptedValue = objectMapper.readValue(decryptedData, elementClass);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            Response response = new Response();
//            response.setResponseCode("99");
//            response.setResponseMessage("An error occurred while decrypting response");
//            return (T) response;
//        }
//        log.info("decrypted value : {}", decryptedValue);
//        return decryptedValue;
//    }
//
//
//    public <T> String encryptResponse(T response) throws Exception {
//    	EncryptionService cryptionService = null;
//    	String aeskey = getAeskey(sourcecode);
//    	String encryptedRes = null;
//
//
//
//		try {
//			String res = objectMapper.writeValueAsString(response);
//			log.info("res======="+res);
//
//			encryptedRes = RsaAesGcmStandard.encryptTextUsingAES(res, aeskey);
//			log.info("Pan african response encryption successful");
//		} catch (JsonProcessingException e) {
//			log.error(e.toString());
//			throw e;
//		} catch (Exception e) {
//			log.error(e.toString(),e);
//			throw e;
//		}
//
//		return encryptedRes;
//	}
//
//    public <T> T decryptWithCredentials (String data, Class<T> elementClass, Credentials credentials) throws BadHeaderValuesException, MissingHeaderException {
//        boolean isValid = isCredentialValid(credentials.getClientid(), credentials.getClientsecret());
//        log.info("credentials.getSourcecode()=="+credentials.getSourcecode());
//        log.info("isValid="+isValid);
//        T decryptedValue = null;
//        if (isValid) {
//            String aeskey = getAeskey(credentials.getSourcecode());
//          log.info("aeskey=="+aeskey);
//            try {
//                String decryptedData = RsaAesGcmStandard.decryptTextUsingAES(data, aeskey);
//                log.info("decryptedData="+decryptedData);
//                decryptedValue = objectMapper.readValue(decryptedData, elementClass);
//                log.info("decryptedValue="+decryptedValue);
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error(e.getMessage());
//            }
//        }
//        log.info("decrypted value : {}", decryptedValue);
//        return decryptedValue;
//    }
//
//
//    public  String getAeskey(String name) throws BadHeaderValuesException {
//        Optional<String> aeskey = Optional.ofNullable(environment.getProperty(name));
//        log.info("source code {} :", aeskey.get());
//        if (!aeskey.isPresent()) {
//            throw new BadHeaderValuesException ("101", "Invalid Header Values");
//        }
//        log.error("source code {} :", aeskey.get());
//        return aeskey.get();
//    }
//
//    public  boolean isCredentialValid (String id, String secret, String sourcecode) throws MissingHeaderException, BadHeaderValuesException {
//        if ((secret == null && id == null && sourcecode == null)) {
//            throw new MissingHeaderException("100", "Missing Header Values");
//        } else if (!id.equals(clientid) || !secret.equals(clientsecret) || !sourcecode.equals(sourcecode)){
//            throw new BadHeaderValuesException ("101", "Invalid Header Values");
//        }
//        return true;
//    }
//
//
//    public  boolean isCredentialValid (String id, String secret) throws MissingHeaderException, BadHeaderValuesException {
//        if ((secret == null && id == null )) {
//            throw new MissingHeaderException("100", "Missing Header Values");
//        } else if (!id.equals(clientid) || !secret.equals(clientsecret)){
//            throw new BadHeaderValuesException ("101", "Invalid Header Values");
//        }
//        return true;
//    }
//
//    public Credentials extractKeys(final HttpServletRequest request) throws Exception {
//        Credentials credentials = new Credentials();
//        credentials.setClientid(request.getHeader("x-client-id"));
//        credentials.setClientsecret(request.getHeader("x-client-secret"));
//        credentials.setSourcecode(request.getHeader("x-source-code"));
//        log.info("Credentials Object {} :", credentials);
//        return credentials;
//    }
//
//
//
//
//
//
//}
