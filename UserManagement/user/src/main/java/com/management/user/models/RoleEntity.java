package com.management.user.models;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "role")
@Entity // This tells Hibernate to make a table out of this class
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id", columnDefinition = "VARCHAR(64)")
    private String roleId;
    @Setter
    @Getter
    @Column(name = "role_name", columnDefinition = "VARCHAR(1024)")
    private String name;


    public RoleEntity(){}

    public RoleEntity(String name) {
        this.name = name;

    }



    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + roleId +
                ", name='" + name+ '\'' +
                '}';
    }
}