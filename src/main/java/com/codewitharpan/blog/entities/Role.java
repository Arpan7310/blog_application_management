package com.codewitharpan.blog.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Role {
    @Id
    private  int id;
    private  String name;


    @ManyToMany(mappedBy = "roles")
    private Set<User> users= new HashSet<>();

}

