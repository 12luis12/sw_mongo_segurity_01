package com.empresa6.repositorio;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.empresa6.entidad.User;



public interface UsuarioRepositorio extends MongoRepository<User,ObjectId> {
	
	@Query("{'email': ?0}")
	public User findByEmail(String email);
	
	@Query("{'_id': ?0}")
	Optional<User> findById(ObjectId id);
	

}
