package com.management.user.models;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "role")
@Entity // This tells Hibernate to make a table out of this class
public class RoleEntity {
    @Id
    @Column(name = "role_id", columnDefinition = "VARCHAR(64)")
    @Getter
    private String roleId;
    @Setter
    @Getter
    @Column(name = "role_name", columnDefinition = "VARCHAR(1024)")
    private String name;


    public RoleEntity(){}

    public RoleEntity(String name, String id) {
        this.name = name;
        this.roleId =id;
    }



    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + roleId +
                ", name='" + name+ '\'' +
                '}';
    }
}