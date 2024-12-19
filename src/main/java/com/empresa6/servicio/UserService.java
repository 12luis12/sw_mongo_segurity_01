package com.empresa6.servicio;


import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;





public interface UserService {

	//List<User> findByEmail(String email);
	 ResponseEntity<String> signUp(Map<String,String> requestMap);
	 ResponseEntity<String> login(Map<String,String> requestMap);
	ResponseEntity<String> solicitarRecuperacionContrasena(Map<String, String> requestMap);
	ResponseEntity<String> restablecerContrasena(Map<String, String> requestMap);
	//ResponseEntity<String> CambiarEstatus(Map<String, String> requestMap);
	ResponseEntity<String> updateUser(Map<String, String> requestMap);
	
	ResponseEntity<String> cambiarEstatus(Map<String, String> requestMap);
	ResponseEntity<String> deleteUser(ObjectId id);
	
	//public User findByEmail(String email);

}
