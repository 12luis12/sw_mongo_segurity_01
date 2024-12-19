package com.empresa6.servicio;



import org.bson.types.ObjectId;

import com.empresa6.entidad.PasswordResetToken;

public interface PasswordResetTokenService {

	PasswordResetToken findByToken(String token);

	PasswordResetToken guardarUsuario(PasswordResetToken usuario);
	
	PasswordResetToken eliminar(ObjectId id);
	
	

}
