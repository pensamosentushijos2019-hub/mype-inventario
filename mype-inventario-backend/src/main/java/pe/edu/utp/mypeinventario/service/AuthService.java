package pe.edu.utp.mypeinventario.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import pe.edu.utp.mypeinventario.repository.Repositories;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;
import java.util.Map;
@Service public class AuthService {
 private final Repositories repos; private final String email; private final byte[] salt=new byte[16]; private final byte[] passwordHash;
 public AuthService(Repositories repos,@Value("${app.demo.email}") String email,@Value("${app.demo.password}") String password) {
  this.repos=repos;this.email=email;new SecureRandom().nextBytes(salt);passwordHash=hash(password);
 }
 private byte[] hash(String password) {
  var spec=new PBEKeySpec(password.toCharArray(),salt,120000,256);
  try {return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();}
  catch(GeneralSecurityException e) {throw new IllegalStateException("No se pudo verificar la contraseña",e);}
  finally {spec.clearPassword();}
 }
 public Map<String,String> login(String correo,String password) {
  if(password==null || password.length()>200 || !MessageDigest.isEqual(passwordHash,hash(password)) || !email.equalsIgnoreCase(correo==null?"":correo.trim())) throw new IllegalArgumentException("Correo o contraseña incorrectos");
  var comerciantes=repos.comerciantes.getAll(); if(comerciantes.isEmpty()) throw new IllegalStateException("Falta la tienda de demostración");
  var c=comerciantes.get(0);return Map.of("email",email,"nombre","Administrador","comercianteId",c.getId(),"negocio",c.getNombreNegocio());
 }
}
