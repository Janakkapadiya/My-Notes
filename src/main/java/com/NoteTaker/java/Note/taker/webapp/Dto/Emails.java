package com.NoteTaker.java.Note.taker.webapp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emails {
    private String name;
    private String email;
    private String subject;
    private String content;
}
