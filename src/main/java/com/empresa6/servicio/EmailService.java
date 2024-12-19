package com.empresa6.servicio;

public interface EmailService {
	void enviarEmailRestablecimiento(String email, String token);
	void enviarEmailConfirmacion(String email, String token);
}
