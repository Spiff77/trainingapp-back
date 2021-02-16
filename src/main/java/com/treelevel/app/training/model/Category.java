package com.treelevel.app.training.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
public @Data class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Program> programs;


}
