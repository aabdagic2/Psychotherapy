package com.management.user.accessingdatamysql;


import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "role")
@Entity // This tells Hibernate to make a table out of this class
public class RoleEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "role_id", columnDefinition = "VARCHAR(16)")
    private UUID roleId;

    @Column(name = "role_name", columnDefinition = "VARCHAR(1024)")
    private String name;




    public UUID getId() {
        return roleId;
    }

    public void setId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    protected RoleEntity(){}

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