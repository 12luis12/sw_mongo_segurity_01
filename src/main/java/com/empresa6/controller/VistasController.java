package com.empresa6.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping(path="/url")
public class VistasController {
	
	 @GetMapping("/formularioLogin")
	    public String login() {
		 log.info("Dentro del jsp login ");
	        return "login"; // Retorna la vista JSP
	    }
	 @GetMapping("/registrarUsuario")
	    public String introducirUsuario() {
		 log.info("Dentro del jsp login ");
	        return "registrarUsuarios"; // Retorna la vista JSP
	        
	    }
	 
	 @GetMapping("/forgot-password")
	    public String forgotpassword() {
		 log.info("Dentro del olvidaste contraseña ");
	        return "enviarEmail"; // Retorna la vista JSP
	        
	    }
	 
	 @GetMapping("/restablecer-contrasena")
	    public String mostrarFormularioRestablecimiento(@RequestParam("token") String token, Model model) {
	        // Añade el token al modelo para que el formulario pueda usarlo
	        model.addAttribute("token", token);
	        return "formularioRestablecimiento"; // Nombre de la vista HTML o JSP
	    }
	 
	 @GetMapping("/mensaje-Exitoso")
	    public String mensajeExitoso() {
		 log.info("Mensaje exitoso ");
	        return "mensaje"; // Retorna la vista JSP
	        
	    }
	 
	 
	 @GetMapping("/mensaje-Registra")
	    public String mensajeRegistro() {
		 log.info("Mensaje exitoso ");
	        return "mensajeRegistro"; // Retorna la vista JSP
	        
	    }
	 
	 @GetMapping("/confirmacion-contrasena")
	    public String confirmaContrasena(@RequestParam("token") String token, Model model) {
		 model.addAttribute("token", token);
	        return "confirmarRegistro"; // una ves que se confirma se direcciona a login
	        
	    }
}
