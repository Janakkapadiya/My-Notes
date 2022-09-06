package com.NoteTaker.java.Note.taker.webapp.Controller;

import com.NoteTaker.java.Note.taker.webapp.Dto.Emails;
import com.NoteTaker.java.Note.taker.webapp.Model.User;
import com.NoteTaker.java.Note.taker.webapp.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class homeController {

    private final UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String goHome()
    {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/email")
    public String email(Model model) {
        model.addAttribute("Emails", new Emails());
        return "email";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, Model model, HttpSession session) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User users = userRepo.save(user);
        if (users != null) {
            session.setAttribute("msg", "user successfully added");
        } else {
            session.setAttribute("msg", "someTing went wrong");
        }
        return "redirect:/signup";
    }
}
