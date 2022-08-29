package com.NoteTaker.java.Note.taker.webapp.Controller;

import com.NoteTaker.java.Note.taker.webapp.Model.User;
import com.NoteTaker.java.Note.taker.webapp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController{

    @Autowired
    private UserRepo userRepo;


    @GetMapping("/addNotes")
    public String home()
    {
        return "user/addNotes";
    }

    @GetMapping("/viewNotes")
    public String addNotes()
    {
        return "user/viewNotes";
    }

    @GetMapping("/editNotes")
    public String updateNotes()
    {
        return "user/editNotes";
    }

    @GetMapping("/viewProfile")
    public String viewProfile()
    {
        return "user/viewProfile";
    }


    //get logged in user details

    @ModelAttribute
    public void getUserName(Model model,Principal principal)
    {
        String email = principal.getName();
        User user = userRepo.findByEmail(email);
        model.addAttribute("user", user);
    }

}
