package com.empresa6.entidad;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.empresa6.util.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="password_reset_token")
public class PasswordResetToken {
    
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId _id;
    
    private String token;
    
    // Almacenar solo el ID del usuario en lugar de un objeto User completo
    private ObjectId userId;
    
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public PasswordResetToken() {
    }

    // Constructor con parámetros
    public PasswordResetToken(String token, ObjectId userId) {
        this.token = token;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusMinutes(30); // Token válido por 30 minutos
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
