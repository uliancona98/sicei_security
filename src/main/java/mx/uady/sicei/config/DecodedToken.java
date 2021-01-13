package mx.uady.sicei.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

public class DecodedToken {

  private String sub;
  private String exp;
  private String iat;
  private String expiracion;

  public static DecodedToken getDecoded(String encodedToken) {
      String[] pieces = encodedToken.split("\\.");
      String b64payload = pieces[1];
      String jsonString = new String(Base64.decodeBase64(b64payload), StandardCharsets.UTF_8);
      System.out.println("PAYLOAD:"+ jsonString);

      return new Gson().fromJson(jsonString, DecodedToken.class);
  }

  public String toString() {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      return gson.toJson(this);
  }

  public String getSub(){
    return this.sub;
  }

  public String getExp(){
    return this.exp;
  }

  public String getIat(){
    return this.iat;
  }

  public String getExpiracion(){
    return this.expiracion;
  }

}