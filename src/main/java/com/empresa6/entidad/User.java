package com.empresa6.entidad;



import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.empresa6.util.ObjectIdSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import lombok.Getter;
import lombok.Setter;
//@NamedQuery(name="User.findByEmail",query="select u from User u where u.email=:email ")
@Getter
@Setter
@Document(collection="usuario")
public class User {
	
	
	@Id
	@JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
	
	private String nombre;
	private String apellido;
	
	private String password;
	
	private String email;
	
	private String role;
	
	private String status;
	
	

	 
	
	
}
