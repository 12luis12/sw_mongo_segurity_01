package com.empresa6.servicio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	 @Autowired
	 private JavaMailSender mailSender;
	 
	 
    @Override
    public void enviarEmailRestablecimiento(String email, String token) {
        String url = "http://localhost:8086/url/restablecer-contrasena?token=" + token;
        String subject = "Recuperación de contraseña";
        String body = "Estimado usuario,\n\n" +
                "Hemos recibido una solicitud para restablecer su contraseña. Para continuar con el proceso, " +
                "haga clic en el siguiente enlace:\n\n" +
                url + "\n\n" +
                "Si no solicitó este cambio, ignore este correo.\n\n" +
                "Saludos,\n" +
                "El equipo de soporte";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        // Enviar el correo
        mailSender.send(message); 
        
    }
    
    
    
    @Override
    public void enviarEmailConfirmacion(String email, String token) {
        String url = "http://localhost:8086/url/confirmacion-contrasena?token=" + token;
        String subject = "Confirmacion de registro";
        String body = "Estimado usuario,\n\n" +
                "Para terminar con el registro. Para continuar con el proceso, " +
                "haga clic en el siguiente enlace:\n\n" +
                url + "\n\n" +
                "si no desea continuar co el registro , ignore este correo.\n\n" +
                "Saludos,\n" +
                "El equipo de soporte";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        // Enviar el correo
        mailSender.send(message); 
        
    }
}
