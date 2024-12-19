package com.empresa6.servicio;



import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.empresa6.repositorio.PasswordResetTokenRepository;
import com.empresa6.entidad.PasswordResetToken;

@Service
public class PasswordResetTokenServiceImpl implements  PasswordResetTokenService{

	
	@Autowired
	private  PasswordResetTokenRepository  passwordResetTokenRepository;
	
	
	
	
	@Override
	public PasswordResetToken guardarUsuario(PasswordResetToken usuario) {
	return passwordResetTokenRepository.insert(usuario);
	}


	@Override
	public PasswordResetToken findByToken(String token) {
	return passwordResetTokenRepository.findByToken(token);
	}
	
	@Override
	public PasswordResetToken eliminar(ObjectId id) {
	    PasswordResetToken token = passwordResetTokenRepository.findById(id).orElse(null);
	    if (token != null) {
	        passwordResetTokenRepository.deleteById(id);
	    }
	    return token;
	}


}
