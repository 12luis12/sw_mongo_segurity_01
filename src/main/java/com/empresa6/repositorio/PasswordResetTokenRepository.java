package com.empresa6.repositorio;



import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import com.empresa6.entidad.PasswordResetToken;

public interface PasswordResetTokenRepository extends MongoRepository <PasswordResetToken,ObjectId> {

	@Query("{'token': ?0}")
	public PasswordResetToken findByToken(String token);
	
}
