package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.entity.User;
import com.app.service.UserService;
import com.app.utils.Constant;
import com.app.validator.LoginValidator;

@Controller
public class HomeController {
	@Autowired
	UserService userService;
	@Autowired
	LoginValidator loginValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
		if(dataBinder.getTarget().getClass() == User.class) {
			dataBinder.setValidator(loginValidator);
		}
	}
	
	@GetMapping(value = {"/","/index","/home"})
	public String index(ModelMap map) {
		return "index";
	}
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginForm", new User());
		return "login";
	}
	@GetMapping("/access-denied")
	public String accessDenied(Model model) {
		return "access-denied";
	}
	
	@PostMapping("/processLogin")
	public String processLogin(Model model,@ModelAttribute("loginForm") @Validated User user, BindingResult result,HttpSession 
			session) {
		if(result.hasErrors()) {
			return "login";
		}
		List<User> lstUser = userService.getAllByProperty("username", user.getUsername());
		if(lstUser != null && lstUser.size() > 0){
			User userInfo = lstUser.get(0);
			session.setAttribute(Constant.USER_INFO, userInfo);
		}
		return "redirect:/index";
	}
	@GetMapping("/logout")
	public String login(HttpSession session) {
		session.removeAttribute(Constant.USER_INFO);
		return "redirect:/index";
	}
	
 
}
