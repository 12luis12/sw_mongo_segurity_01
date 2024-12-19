package com.empresa6.servicio;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.empresa6.constantes.FacturasConstantes;
import com.empresa6.entidad.PasswordResetToken;
import com.empresa6.entidad.User;
import com.empresa6.repositorio.UsuarioRepositorio;
import com.empresa6.security.CustomerDetailsService;
import com.empresa6.security.jwt.JwtUtil;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomerDetailsService customerDetailsService;
	@Autowired
	private  UsuarioRepositorio  usuarioRepositorio;
	@Autowired
	private  PasswordResetTokenService  passwordResetTokenService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private EmailService EmailService;
	
	
	     
	
	
	
	
	
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
	    log.info("Registro interno de un usuario: {}", requestMap);
	    try {
	        if (validateSignUpMap(requestMap)) {
	            User user =  usuarioRepositorio.findByEmail(requestMap.get("email"));
	            if (Objects.isNull(user)) {
	                // Crear un nuevo usuario a partir del mapa
	                User newUser = getUserFromMap(requestMap);
	                
	                // Codificar la contraseña antes de guardar
	                String encodedPassword = passwordEncoder.encode(newUser.getPassword());
	                newUser.setPassword(encodedPassword);

	                // Guardar el usuario en el repositorio
	                usuarioRepositorio.save(newUser);
	                //--------------------------------------------------------
	                // Generar un token de recuperación
	    	        String token = UUID.randomUUID().toString();

	    	        // Guardar el token con la fecha de creación (y posible expiración)
	    	         passwordResetTokenService.guardarUsuario(new PasswordResetToken(token, newUser.getId()));

	    	        EmailService.enviarEmailConfirmacion(newUser.getEmail(), token);
                // Devolver el mensaje y el correo en una cadena
	                String responseMessage = String.format("Mensaje: Usuario registrado con éxito. Correo: %s", newUser.getEmail());
	                return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity<>("Error: El usuario con ese email ya existe", HttpStatus.BAD_REQUEST);
	            }
	        } else {
	            return new ResponseEntity<>(FacturasConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	    return new ResponseEntity<>(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
	
	
	
	
	
	private boolean validateSignUpMap(Map<String, String> requestMap) {
		if(requestMap.containsKey("nombre")&&requestMap.containsKey("apellido")&&requestMap.containsKey("email")&&requestMap.containsKey("password")) {
			return true;
		}
		return false;
	}
	
	
	
	
	private User getUserFromMap(Map<String, String> requestMap) {
		User user=new User();
		user.setNombre(requestMap.get("nombre"));
		user.setApellido(requestMap.get("apellido"));
		user.setEmail(requestMap.get("email"));
		user.setPassword(requestMap.get("password"));
		user.setStatus("false");
		user.setRole("user");
		return user;
	}
	
	
	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
	    log.info("Dentro del login");
	    try {
	        // Extraer email y password del requestMap
	        String email = requestMap.get("email");
	        String password = requestMap.get("password");

	        // Verificar si el usuario existe
	        if (!customerDetailsService.authenticateUser(email, password)) {
	            return new ResponseEntity<>(
	                "{\"mensaje\":\"E-mail y/o contraseña son incorrectas, vuelva a intentar por favor\"}", 
	                HttpStatus.NOT_FOUND
	            );
	        }

	        // Autenticar al usuario
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(email, password)
	        );

	        if (authentication.isAuthenticated()) {
	            // Obtener los detalles del usuario
	            var userDetails = customerDetailsService.getUserDetail();

	            // Verificar si el usuario está activo
	            if (userDetails.getStatus().equalsIgnoreCase("true")) {
	                // Generar el token con email, role, userId y nombre
	                String token = jwtUtil.generateToken(
	                    userDetails.getEmail(), 
	                    userDetails.getRole(),
	                    userDetails.getId(),     // Agregar userId
	                    userDetails.getNombre()      // Agregar nombre
	                );

	                return new ResponseEntity<>(
	                    "{\"token\":\"" + token + "\"}", 
	                    HttpStatus.OK
	                );
	            } else {
	                return new ResponseEntity<>(
	                    "{\"mensaje\":\"Espere la aprobación del administrador\"}", 
	                    HttpStatus.BAD_REQUEST
	                );
	            }
	        }
	    } catch (Exception exception) {
	        log.error("Error during login: {}", exception);
	    }
	    return new ResponseEntity<>(
	        "{\"mensaje\":\"Credenciales incorrectas\"}", 
	        HttpStatus.BAD_REQUEST
	    );
	}

	
	
	@Override
	public ResponseEntity<String> solicitarRecuperacionContrasena(Map<String, String> requestMap) {
	    log.info("Registro dentro de recuperacion de contrasena: {}", requestMap);
	    try {
	        String email = requestMap.get("email");
	        User user = usuarioRepositorio.findByEmail(email);
	        
	        if (user == null) {
	            return new ResponseEntity<>("{\"mensaje\":\"El usuario no existe registrado, registrese porfavor \", \"correo\":null}", HttpStatus.NOT_FOUND);
	        }
	        
	        // Generar un token de recuperación
	        String token = UUID.randomUUID().toString();

	        // Guardar el token con la fecha de creación (y posible expiración)
	        //passwordResetTokenRepository.save(new PasswordResetToken(token, user));
	        passwordResetTokenService.guardarUsuario(new PasswordResetToken(token, user.getId()));


	        // Enviar email al usuario con el enlace de recuperación (URL de tu sistema + token)
	        EmailService.enviarEmailRestablecimiento(user.getEmail(), token);

	        // Retornar el mensaje y el correo del usuario
	        return new ResponseEntity<>("{\"mensaje\":\"Revise su correo electrónico para continuar\", \"correo\":\"" + user.getEmail() + "\"}", HttpStatus.OK);

	    } catch (Exception exception) {
	        log.error("Error en la recuperación de contraseña: {}", exception.getMessage(), exception);
	        return new ResponseEntity<>("{\"mensaje\":\"Ha ocurrido un error al procesar la solicitud\", \"correo\":null}", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}






	@Override
	public ResponseEntity<String> restablecerContrasena(Map<String, String> requestMap) {
	    try {
	        String token = requestMap.get("token");
	        String nuevaContrasena = requestMap.get("password");

	        // Validación opcional de la nueva contraseña
	        if (nuevaContrasena == null || nuevaContrasena.length() < 8) {
	            return new ResponseEntity<>("{\"mensaje\":\"La nueva contraseña debe tener al menos 8 caracteres\"}", HttpStatus.BAD_REQUEST);
	        }

	        // Buscar el token en la base de datos
	        PasswordResetToken resetToken = passwordResetTokenService.findByToken(token);

	        // Validar el token y su estado de expiración
	        if (resetToken == null || resetToken.isExpired()) {
	            return new ResponseEntity<>("{\"mensaje\":\"Token inválido o expirado\"}", HttpStatus.UNAUTHORIZED);
	        }

	        // Obtener el usuario asociado al token usando el userId
	        Optional<User> userOptional = usuarioRepositorio.findById(resetToken.getUserId());
	        if (userOptional.isEmpty()) {
	            return new ResponseEntity<>("{\"mensaje\":\"Usuario no encontrado\"}", HttpStatus.NOT_FOUND);
	        }

	        User user = userOptional.get();

	        // Cambiar la contraseña del usuario y guardarla en la base de datos
	        user.setPassword(passwordEncoder.encode(nuevaContrasena));
	        usuarioRepositorio.save(user);

	        // Eliminar el token temporal de restablecimiento de contraseña
	        passwordResetTokenService.eliminar(resetToken.get_id());

	        return new ResponseEntity<>("{\"mensaje\":\"Contraseña restablecida con éxito\"}", HttpStatus.OK);

	    } catch (Exception exception) {
	        log.error("Error en la recuperación de contraseña: {}", exception.getMessage(), exception);
	        return new ResponseEntity<>("{\"mensaje\":\"Ha ocurrido un error al restablecer la contraseña\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



	@Override
	public ResponseEntity<String> cambiarEstatus(Map<String, String> requestMap) {
	    try {
	        String token = requestMap.get("token");

	        // Buscar el token en la base de datos
	        PasswordResetToken resetToken = passwordResetTokenService.findByToken(token);

	        // Validar si el token es nulo o está expirado
	        if (resetToken == null || resetToken.isExpired()) {
	            return new ResponseEntity<>("{\"mensaje\":\"Token inválido o expirado\"}", HttpStatus.UNAUTHORIZED);
	        }

	        // Obtener el usuario asociado al token usando el userId almacenado en PasswordResetToken
	        Optional<User> userOptional = usuarioRepositorio.findById(resetToken.getUserId());
	        if (userOptional.isEmpty()) {
	            return new ResponseEntity<>("{\"mensaje\":\"Usuario no encontrado\"}", HttpStatus.NOT_FOUND);
	        }

	        User user = userOptional.get();

	        // Cambiar el estatus del usuario a "true"
	        user.setStatus("true");
	        usuarioRepositorio.save(user);

	        // Eliminar el token después de haberlo usado
	        passwordResetTokenService.eliminar(resetToken.get_id());

	        return new ResponseEntity<>("{\"mensaje\":\"Estado actualizado con éxito\"}", HttpStatus.OK);

	    } catch (Exception exception) {
	        log.error("Error al actualizar el estado: {}", exception.getMessage(), exception);
	        return new ResponseEntity<>("{\"mensaje\":\"Ha ocurrido un error al actualizar el estado\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@Override
	public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
	    log.info("Actualización de usuario: {}", requestMap);
	    try {
	        // Verificar que el mapa contenga los datos necesarios, incluido el ID
	        if (validateSignUpMap(requestMap) && requestMap.containsKey("_id")) {
	            ObjectId userId;
	            try {
	                userId = new ObjectId(requestMap.get("_id"));
	            } catch (IllegalArgumentException e) {
	                return new ResponseEntity<>("{\"mensaje\":\"Error: id no válido\"}", HttpStatus.BAD_REQUEST);
	            }

	            Optional<User> optionalUser = usuarioRepositorio.findById(userId);
	            if (optionalUser.isPresent()) {
	                User user = optionalUser.get();
	                // Actualizar solo los campos presentes en el mapa, excluyendo el ID
	                if (requestMap.containsKey("password")) {
	                    String encodedPassword = passwordEncoder.encode(requestMap.get("password"));
	                    user.setPassword(encodedPassword);
	                }
	                if (requestMap.containsKey("nombre")) {
	                    user.setNombre(requestMap.get("nombre"));
	                }
	                if (requestMap.containsKey("apellido")) {
	                    user.setApellido(requestMap.get("apellido"));
	                }
	                if (requestMap.containsKey("email")) {
	                    user.setEmail(requestMap.get("email"));
	                }
	                
	                // Guardar el usuario actualizado en el repositorio
	                usuarioRepositorio.save(user);

	                String responseMessage = String.format(
	                    "{\"mensaje\":\"Usuario actualizado con éxito. ID: %s, correo: %s\"}",
	                    user.getId(), user.getEmail());
	                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>("{\"mensaje\":\"Error: el usuario no existe\"}", HttpStatus.NOT_FOUND);
	            }
	        } else {
	            return new ResponseEntity<>(FacturasConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	    return new ResponseEntity<>(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
	
	@Override
	public ResponseEntity<String> deleteUser(ObjectId id) {
	    log.info("Eliminación de usuario con ID: {}", id);
	    try {
	        // Verificar si el usuario con el ID proporcionado existe
	        Optional<User> optionalUser = usuarioRepositorio.findById(id);
	        if (optionalUser.isPresent()) {
	            usuarioRepositorio.deleteById(id);
	            return new ResponseEntity<>("{\"mensaje\":\"Usuario eliminado con exito \"}", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("{\"mensaje\":\"Error el usuario no existe \"}", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	        return new ResponseEntity<>(FacturasConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}








	

	
}
