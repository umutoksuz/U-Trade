package Utils;

import Models.User;
import Services.UserDataService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Cryptography {

    private static final String JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
    private static final String SECRET = "KADFAHGJRDDAFAJFANDFAKDNSAFKGRMVTBLTL6ADA5457A4";

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return new String(salt);
    }

    public static String generateSignature(String data) {

        try {
            byte[] hash = SECRET.getBytes(StandardCharsets.UTF_8);

            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return encode(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e){
            return null;
        }
    }

    public static String generateJWTToken(int userId){
        JsonObject payload = new JsonObject();
        payload.addProperty("sub", userId);
        payload.addProperty("aud", "strade_users");
        String data = encode(JWT_HEADER.getBytes(StandardCharsets.UTF_8)) + "." + encode(payload.toString().getBytes(StandardCharsets.UTF_8));
        String signature = generateSignature(data);
        return data + "." + signature;
    }

    public static User validateToken (String encodedToken) {
        try {
            String[] parts = encodedToken.split("\\.");
            Gson gson = new Gson();
            Payload payload = gson.fromJson(decode(parts[1]), Payload.class);

            if (parts[2].equals(generateSignature(parts[0]+ "." + parts[1]))){
                return UserDataService.getUserById(Integer.parseInt(payload.sub));
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    private static String encode(byte[] bytes){
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String decode (String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }
}

class Payload{
    public String aud;
    public String sub;
}
