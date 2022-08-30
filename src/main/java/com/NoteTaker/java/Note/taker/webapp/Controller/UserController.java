package com.NoteTaker.java.Note.taker.webapp.Controller;

import com.NoteTaker.java.Note.taker.webapp.Model.Notes;
import com.NoteTaker.java.Note.taker.webapp.Model.User;
import com.NoteTaker.java.Note.taker.webapp.Dto.UserOrders;
import com.NoteTaker.java.Note.taker.webapp.Repository.NotesRepo;
import com.NoteTaker.java.Note.taker.webapp.Repository.UserRepo;
import com.NoteTaker.java.Note.taker.webapp.Service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private NotesRepo notesRepo;

    @Autowired
    private PaypalService paypalService;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/addNotes")
    public String home() {
        return "user/addNotes";
    }

    @GetMapping("/viewNotes/{page}")
    public String addNotes(@PathVariable int page, Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepo.findByEmail(email);

        Pageable pageable = PageRequest.of(page, 3, Sort.by("id").descending());
        Page<Notes> notesByUser = notesRepo.findNotesByUser(user.getId(), pageable);

        model.addAttribute("pageNo", page);
        model.addAttribute("totalPage", notesByUser.getTotalPages());
        model.addAttribute("Notes", notesByUser);
        model.addAttribute("totalElement", notesByUser.getTotalElements());

        return "user/viewNotes";
    }

    @GetMapping("/search")
    public String searchByTitle(Model model, String keyword) {
        if (keyword != null) {
            List<Notes> searchNotes = notesRepo.findByKeyword(keyword);
            model.addAttribute("searchNotes", searchNotes);
            model.addAttribute("keyword", keyword);
            model.addAttribute("pageTitle", "results of the keywords you have entered ' " + keyword + " ' ");
        }
        return "user/searchResults";
    }

    @GetMapping("/editNotes/{id}")
    public String updateNotes(Model model, @PathVariable int id) {
        Optional<Notes> note = notesRepo.findById(id);
        if (note.isPresent()) {
            Notes notesById = note.get();
            model.addAttribute("notesById", notesById);
        }
        return "user/editNotes";
    }

    @GetMapping("/deleteNotes/{id}")
    public String deleteNotes(Model model, @PathVariable int id, HttpSession session) {
        Optional<Notes> note = notesRepo.findById(id);
        if (note.isPresent()) {
            notesRepo.delete(note.get());
            session.setAttribute("msg", "note updated successfully");
        }
        return "redirect:/viewNotes/0";
    }

    @PostMapping("/updateNotes")
    public String updateNotes(@ModelAttribute Notes notes, Principal principal, HttpSession session) {
        String email = principal.getName();
        User user = userRepo.findByEmail(email);
        notes.setUser(user);
        Notes note = notesRepo.save(notes);
        if (note != null) {
            session.setAttribute("msg", "note updated successfully");
        } else {
            session.setAttribute("msg", "note not updated successfully");
        }
        return "redirect:/viewNotes/0";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user, HttpSession session) {
        Optional<User> oldUser = userRepo.findById(user.getId());
        if (oldUser.isPresent()) {
            user.setName(oldUser.get().getName());
            user.setEmail(oldUser.get().getEmail());
            User saveUpdates = userRepo.save(user);
            if (saveUpdates != null) {
                session.setAttribute("msg", "user updated successfully");
            }
        }
        return "redirect:/viewProfile";
    }

    @GetMapping("/viewProfile")
    public String viewProfile() {
        return "user/viewProfile";
    }


    //get logged in user details

    @ModelAttribute
    public void getUserName(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepo.findByEmail(email);
        model.addAttribute("user", user);
    }

    //ends of getting user details

    @PostMapping("/saveNotes")
    public String saveNotes(@ModelAttribute Notes notes, HttpSession session, Principal principal) {
        String email = principal.getName();
        User user = userRepo.findByEmail(email);
        notes.setUser(user);
        Notes n = notesRepo.save(notes);
        if (n != null) {
            session.setAttribute("msg", "note saved successfully");
        } else {
            session.setAttribute("msg", "could not save notes");
        }
        return "redirect:/addNotes";
    }


    @PostMapping("/pay")
    public String payment(@ModelAttribute("order") UserOrders order) {
        try {
            Payment payment = paypalService.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:5656/" + CANCEL_URL,
                    "http://localhost:5656/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
//            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
//            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/goToPaymentPage")
    public String goToPaymentPage() {
        return "paymentForm";
    }
}
