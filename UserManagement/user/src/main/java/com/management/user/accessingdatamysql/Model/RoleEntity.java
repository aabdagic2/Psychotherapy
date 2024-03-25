package com.management.user.accessingdatamysql.Model;


import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "role")
@Entity // This tells Hibernate to make a table out of this class
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id", columnDefinition = "VARCHAR(64)")
    private String roleId;

    @Column(name = "role_name", columnDefinition = "VARCHAR(1024)")
    private String name;




    public String getId() {
        return roleId;
    }

    public void setId(String roleId) {
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