package com.treelevel.app.training.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public @Data class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NonNull
    private String firstname;

    @NonNull
    private String lastname;

    @NonNull
    private LocalDate birthdate;

    private String street;

    private String complementary;

    private String city;

    private String postcode;

    private String photofilename;
}
