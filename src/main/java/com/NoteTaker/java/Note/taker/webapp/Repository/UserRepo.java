package com.NoteTaker.java.Note.taker.webapp.Repository;

import com.NoteTaker.java.Note.taker.webapp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    public User findByEmail(String email);

}
