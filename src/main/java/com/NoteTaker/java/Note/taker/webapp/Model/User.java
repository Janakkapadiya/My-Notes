package com.NoteTaker.java.Note.taker.webapp.Model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "full_Name")
    private String name;
    private String email;
    private String password;
    private String about;
}
