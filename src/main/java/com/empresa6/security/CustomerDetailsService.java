package com.empresa6.security;


import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.empresa6.entidad.User;
import com.empresa6.repositorio.UsuarioRepositorio;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CustomerDetailsService implements UserDetailsService {
	@Autowired
    private  UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder ;
	
	private User userDetail;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	log.info("Dentro de loadUserByUsername {}",username);
	userDetail= (User) usuarioRepositorio.findByEmail(username);
     if(!Objects.isNull(userDetail)) {
    	 return new org.springframework.security.core.userdetails.User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
     }
     else {
    	 throw new UsernameNotFoundException("Usuario no encontrado");
     }
	}
  public User getUserDetail() {
	  return userDetail;
  }

  public boolean authenticateUser(String email, String password) {
 	    User user = (User) usuarioRepositorio.findByEmail(email);
 	    
 	    // Verificar si el usuario existe y si la contraseña coincide
 	    return user != null && passwordEncoder.matches(password, user.getPassword());
 	}
  
  
  public boolean userExists(String email) {
	    // Implement logic to check if the user exists in the database
	    // For example, using a UserRepository:
	    return usuarioRepositorio.findByEmail(email) != null;
	}
}
