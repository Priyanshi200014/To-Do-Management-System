package springbootproject.todolist.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springbootproject.todolist.createuser.CreateUser;
import springbootproject.todolist.entity.User;
import springbootproject.todolist.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder)
	{
		StringTrimmerEditor stringtrim = new StringTrimmerEditor(true);	
		webDataBinder.registerCustomEditor(String.class,stringtrim);
		
	}
	
	
	@GetMapping("/showRegistrationForm")
	public String showMyRegistartionPage(Model theModel)
	{
		CreateUser cuser = new CreateUser();
		
		theModel.addAttribute("cuser" , cuser);
		
		return "registration-form";
	}
	
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("cuser") CreateUser theCreateUser , 
			BindingResult theBindingResult , 
			Model theModel) {
		
		String username = theCreateUser.getUserName();
		
		logger.info("The Username = " + username);
		
		if(theBindingResult.hasErrors())
		{
			return "registration-form";
		}
		
		User existingUser = userService.findByUsername(username);
		
		if(existingUser != null)
		{
			theModel.addAttribute("cuser" , new CreateUser());
			theModel.addAttribute("registrationError" , "UserName already Exists");
			
			logger.warning("User already Exists !!! ");
			
			return "registration-form";
			
		}
		
		userService.save(theCreateUser);
		
		logger.info("User saved Successfully!!!!");
		
		return "registration-confirmation";
		
	}
	
}
