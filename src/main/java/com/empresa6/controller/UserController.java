package com.empresa6.controller;


import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.empresa6.constantes.FacturasConstantes;
import com.empresa6.servicio.UserService;
import com.empresa6.util.FacturaUtils;



@RestController
@RequestMapping(path="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> registrarUsuario(@RequestBody(required=true)Map<String,String> requestMap){
		try {
			return userService.signUp(requestMap);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return FacturaUtils.getResponseEntity(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/login")
	 public ResponseEntity<String>login(@RequestBody(required=true) Map<String,String> requestMap){
		 try {
			return userService.login(requestMap);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		 return FacturaUtils.getResponseEntity(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	 }	
	@PutMapping("/update") // Utiliza PUT para actualizar
    public ResponseEntity<String> updateUser(@RequestBody Map<String, String> requestMap) {
       try {
    	   return userService.updateUser(requestMap);
       } catch (Exception exception) {
			exception.printStackTrace();
			return FacturaUtils.getResponseEntity(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
    }



     @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable ObjectId id) {
        try {
        	return userService.deleteUser(id);
        } catch (Exception exception) {
			exception.printStackTrace();
			return FacturaUtils.getResponseEntity(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
			 
		}
    }
     
     
     

 	@PostMapping("/solicitaRecuperar-contrasena")
 	public ResponseEntity<String> solicitarRecuperacion(@RequestBody(required=true) Map<String, String> requestMap) {
 		try {
 			return userService.solicitarRecuperacionContrasena(requestMap);
 		} catch (Exception exception) {
 			exception.printStackTrace();
 		}
 		return FacturaUtils.getResponseEntity(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
 		
 	   }
 	
 	
 	

 	
 	
 	
 	@PostMapping("/procesar-restablecimiento")
 	public ResponseEntity<String> restablecerContrasena(@RequestBody(required=true) Map<String, String> requestMap) {
 	    try {
 	        // Llamada al servicio para restablecer la contraseña
 	        ResponseEntity<String> response = userService.restablecerContrasena(requestMap);
 	        
 	        // Retornar la respuesta del servicio directamente
 	        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
 	    } catch (Exception exception) {
 	        exception.printStackTrace();
 	        return FacturaUtils.getResponseEntity(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
 			
 	    }
 	}


 	@PostMapping("/confirmacion-contrasena")
 	public ResponseEntity<String> confirmacioncontrsena(@RequestBody(required=true) Map<String, String> requestMap) {
 	    try {
 	        // Llamada al servicio para restablecer la contraseña
 	        ResponseEntity<String> response = userService.restablecerContrasena(requestMap);
 	        
 	        // Retornar la respuesta del servicio directamente
 	        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
 	    } catch (Exception exception) {
 	        exception.printStackTrace();
 	        return FacturaUtils.getResponseEntity(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
 			
 	    }
 	}
 	
 	@PostMapping("/confirmacion-registroUsuario")
 	public ResponseEntity<String> confirmacionRegistroUsuario(@RequestBody(required=true) Map<String, String> requestMap) {
 	    try {
 	        // Llamada al servicio para restablecer la contraseña
 	        ResponseEntity<String> response = userService.cambiarEstatus(requestMap);
 	        
 	        // Retornar la respuesta del servicio directamente
 	        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
 	    } catch (Exception exception) {
 	        exception.printStackTrace();
 	        return FacturaUtils.getResponseEntity(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
 			
 	    }
 	}
	
}
