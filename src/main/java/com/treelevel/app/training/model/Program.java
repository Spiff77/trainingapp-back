package com.treelevel.app.training.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public @Data class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private String ref;

    @NonNull
    private double days;

    @NonNull
    private String objectives;

    @NonNull
    private String prerequisites;

    @NonNull
    @Lob
    private String content;

    @NonNull
    private double priceIntra;

    @NonNull
    private double priceInter;

    @NonNull
    private boolean active;

    @NonNull
    private Visibility visibility;

    @ManyToMany()
    private Set<Category> categories;
}
