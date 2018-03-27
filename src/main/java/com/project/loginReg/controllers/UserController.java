package com.project.loginReg.controllers;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.loginReg.models.User;
import com.project.loginReg.services.UserService;

@Controller
public class UserController{
	private UserService uS;

	public UserController(UserService uS){
		this.uS=uS;  //this sets UserService to a variable called uS
	}
	
	// @RequestMapping("/registration")
	// public String registerForm(@Valid @ModelAttribute("user") User user) {
	// 	return "registrationPage";
    // }

	@PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		if (result.hasErrors()) {
			System.out.println("Errors are here.");
            return "loginPage";
        }

		if(uS.findByEmail( user.getEmail() ) != null ){
			model.addAttribute("emaiError", "A user with this email already exists.");
			return "loginPage";
		}
		if(uS.findByUsername(user.getUsername()) != null ){
			model.addAttribute("usernameError","A user with this username already exists.");
			return "loginPage";
		}

        if(uS.all().size() < 1){
            uS.saveAdmin(user);
        }else{
            uS.saveUser(user);
        }

        System.out.println("User has been saved");
        return "redirect:/login";
    }
    
	@RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, @Valid @ModelAttribute("user") User user) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "loginPage";
    }

	@RequestMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
        String username = principal.getName();
        User user = uS.findByUsername(username);

        model.addAttribute("currentUser", user);

        if (user.checkIfAdmin()) {
			return "redirect:/admin";
		}

        return "homePage";
    }

	@RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", uS.findByUsername(username));
        if (!uS.findByUsername(username).checkIfAdmin()){
            return "redirect:/loginPage";
        }
        return "adminPage";
    }
}

//principal is a secure session
