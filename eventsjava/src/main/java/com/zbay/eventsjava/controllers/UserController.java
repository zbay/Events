package com.zbay.eventsjava.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zbay.eventsjava.models.Event;
import com.zbay.eventsjava.models.Message;
import com.zbay.eventsjava.models.User;
import com.zbay.eventsjava.services.EventService;
import com.zbay.eventsjava.services.UserService;
import com.zbay.eventsjava.validators.UserValidator;

@Controller
public class UserController {
    private UserService userService;
    private UserValidator userValidator;
    private EventService eventService;
    
    private static ArrayList<String> states = new ArrayList<String>();
    
    public UserController(UserService userService, UserValidator userValidator, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
        this.userValidator = userValidator;
        states.add("VA");
        states.add("MD");
        states.add("DC");
        states.add("NC");
        states.add("SC");
        states.add("WV");
        states.add("PA");
    }
    
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
    
    @RequestMapping(value= {"/", "/login", "/registration"})
    public String home(HttpSession session, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
            session.setAttribute("successReg", false);
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
            session.setAttribute("successReg", false);
        }
    	model.addAttribute("user", new User());
    	model.addAttribute("states", states);
        return "loginRegistration";
    }
    
    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
    	userValidator.validate(user, result);
    	if(result.hasErrors()) {
    		return "loginRegistration";
    	}
    	else{
    		this.userService.saveWithUserRole(user);	
        	session.setAttribute("successReg", true);
    		return "redirect:/login";
    	}
    }
    
    @RequestMapping("/events")
    public String events(Principal principal, Model model, HttpSession session, @Valid @ModelAttribute("newEvent") Event newEvent) {
    	session.setAttribute("successReg", false);
        String email = principal.getName();
        User user = userService.findByEmail(email).get(0);
        this.userService.update(user);
        model.addAttribute("currentUser", user);
        model.addAttribute("events", this.eventService.getAll());
        model.addAttribute("states", states);
        return "events";
    }
    
    @RequestMapping(value="/events", method=RequestMethod.POST)
    public String newEvent(@Valid @ModelAttribute("newEvent") Event event, Principal principal, BindingResult result, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email).get(0);
        this.userService.update(user);
    	if(result.hasErrors()) {
    		return "events";
    	}
    	else {
    		this.eventService.saveEvent(user, event);
    		return "redirect:/events";
    	}
    }

    @RequestMapping("/events/{id}/join")
    public String joinEvent(@PathVariable("id") long id, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email).get(0);
    	this.eventService.joinEvent(user, id);
    	return "redirect:/events";
    }
    
    @RequestMapping("/events/{id}/cancel")
    public String cancelEvent(@PathVariable("id") long id, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email).get(0);
    	this.eventService.leaveEvent(user, id);
    	return "redirect:/events";
    }
   
    @RequestMapping("/events/{id}/delete")
    public String deleteEvent(@PathVariable("id") long id) {
    	this.eventService.deleteEvent(id);
    	return "redirect:/events";
    }
    
    @RequestMapping("/events/{id}/edit")
    public String editEvent(@PathVariable("id") long id, Model model) {
    	model.addAttribute("event", this.eventService.getEventById(id));
    	model.addAttribute("states", states);
    	return "edit";
    }

    @RequestMapping(value="/events/{id}/edit", method=RequestMethod.POST)
    public String editEventSubmit(@PathVariable("id") long id, @Valid @ModelAttribute("event") Event event, BindingResult result, Model model) {
    	if(result.hasErrors()) {
    		return "edit";
    	}
    	else {
    		this.eventService.editEvent(id, event);
    		return "redirect:/events";	
    	}
    }
    
    @RequestMapping(value="/events/{id}", method=RequestMethod.GET)
    public String event(@PathVariable("id") long id, Model model) {
    	model.addAttribute("event", this.eventService.getEventById(id));
    	return "event";
    }
    
    @RequestMapping(value="/events/{id}", method=RequestMethod.POST)
    public String addComment(@PathVariable("id") long id, Principal principal, @RequestParam(value="text", required=true) String text) {
    	if(text.length() == 0) {
    		return "event";
    	}
    	else {
            String email = principal.getName();
            User user = userService.findByEmail(email).get(0);
            Message message = new Message();
            message.setText(text);
    		this.eventService.addMessage(message, id, user);
    		return "redirect:/events/" + id;	
    	}
    }
    
}
