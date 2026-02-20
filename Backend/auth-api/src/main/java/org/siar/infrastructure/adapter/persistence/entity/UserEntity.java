package org.siar.infrastructure.adapter.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    public String id;

    @Column(name = "username", unique = true, nullable = false)
    public String username;

    @Column(name = "email", unique = true, nullable = false)
    public String email;

    @Column(name = "password", nullable = false)
    public String password;

    // Default constructor for JPA
    public UserEntity() {
        this.id = UUID.randomUUID().toString(); // Generate ID on creation
    }

    // Constructor for mapping from domain model
    public UserEntity(String id, String username, String email, String password) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
