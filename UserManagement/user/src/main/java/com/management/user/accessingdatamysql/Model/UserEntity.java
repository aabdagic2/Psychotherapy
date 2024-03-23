package com.management.user.accessingdatamysql.Model;


import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "user")
@Entity // This tells Hibernate to make a table out of this class
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", columnDefinition = "VARCHAR(64)")
    private String userId;

    @Column(name = "user_name", columnDefinition = "VARCHAR(1024)")
    private String name;

    @Column(name = "email", columnDefinition = "VARCHAR(256)")
    private String email;
    @Column(name = "passwordHash", columnDefinition = "VARCHAR(256)")
    private String passwordHash;


    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }


    public String getId() {
        return userId;
    }

    public void setId(String idUser) {
        this.userId = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    protected UserEntity(){}

    public UserEntity(String email, String name, String passwordHash) {
        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public UserEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}