package com.stuartyee.javabelt.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stuartyee.javabelt.models.Idea;
import com.stuartyee.javabelt.models.User;
import com.stuartyee.javabelt.services.IdeaService;
import com.stuartyee.javabelt.services.UserService;
import com.stuartyee.javabelt.validators.UserValidator;

@Controller
public class MainController {
	
	@Autowired
	private final UserService uServ;
	@Autowired 
	private final UserValidator uVal;
	@Autowired
	private final IdeaService iServ;
	
	
	
	public MainController(UserService uServ, UserValidator uVal, IdeaService iServ) {
		this.uServ = uServ;
		this.uVal = uVal;
		this.iServ = iServ;
	}

	//Get and Post pair for Login/Registration Page
	@GetMapping("/")
	public String gotoLoginReg(@ModelAttribute("newUser") User user) {
		
		return "logreg.jsp";
	}
	
	@PostMapping("/")
	public String register(@Valid @ModelAttribute("newUser") User user, BindingResult result) {
		uVal.validate(user, result);
		if (result.hasErrors()){
			return "logreg.jsp";
		} else {
			uServ.saveUser(user);
			return "redirect:/"; 
		}
		
	}
	
	//login from Login/Registration Page
	@PostMapping("/login")
	public String login(@RequestParam("logEmail") String email, 
			@RequestParam("logPassword") String passwordEntry, HttpSession session, RedirectAttributes redAttr) {
		
		if(uServ.authenticateUser(email, passwordEntry)) {
			session.setAttribute("UserId", uServ.findUserByEmail(email).getId());
			return "redirect:/ideas";
		} else {
			redAttr.addFlashAttribute("loginError", "Sorry, invalid email/password combination");
			return "redirect:/";
		}
	}
	
	//Got to ideas page
	@GetMapping("/ideas")
	public String gotoIdeas(HttpSession session, Model model) {
		if(session.getAttribute("UserId") == null) {
			return "redirect:/";
		} else {
			User user = uServ.findById((Long)session.getAttribute("UserId"));
			model.addAttribute("loggedIn", user);
			model.addAttribute("ideas", iServ.findAllIdeas()); //for now, just spit out all ideas
			return "ideas.jsp";
		}
	}
	
	//new idea GET and POST pair for MVC form
	@GetMapping("/ideas/new")
	public String draftNewIdea(@ModelAttribute("newIdea") Idea idea, HttpSession session) {
		if(session.getAttribute("UserId") == null) {
			return "redirect:/";
		} else {
			return "newIdea.jsp";
		}
	}
	
	@PostMapping("/ideas/new")
	public String saveNewIdea(@Valid @ModelAttribute("newIdea") Idea idea, BindingResult result, HttpSession session) {
		if(session.getAttribute("UserId") == null) {
			return "redirect:/";
		} else {
			User user = uServ.findById((Long)session.getAttribute("UserId"));
			iServ.createIdea(user, idea);

			return "redirect:/ideas";
		}
	}
	
	//Details page for idea no editing
	@GetMapping("/ideas/{id}")
	public String showIdea(@PathVariable("id") Long id, Model model, HttpSession session) {
		if(session.getAttribute("UserId") == null) {
			return "redirect:/";
		} else {
			User user = uServ.findById((Long)session.getAttribute("UserId"));
			model.addAttribute("thisIdea", iServ.findIdeaById(id));
			model.addAttribute("loggedIn", user);

			return "detail.jsp";
		}
	}
	
	@GetMapping("ideas/{id}/edit")
	public String editScreen(@PathVariable("id") Long id, Model model, HttpSession session) {

		Long user__id = (Long)session.getAttribute("UserId");
		Long creator__id = iServ.findIdeaById(id).getCreator().getId();
		if(session.getAttribute("UserId") == null) {
			return "redirect:/";
		} else if (user__id.intValue() == creator__id.intValue()) {
			model.addAttribute("thisIdea", iServ.findIdeaById(id));
			return "editIdea.jsp";
		} else {
			System.out.println(user__id);
			System.out.println(user__id.TYPE);
			System.out.println(creator__id);
			System.out.println(creator__id.TYPE);
			return "redirect:/ideas";
			
		}
	}
	
	@PostMapping("ideas/{id}/edit")
	public String updateIdea(@PathVariable("id") Long id, @RequestParam("entry") String entry) {
		Idea idea = iServ.findIdeaById(id);
		idea.setName(entry);
		iServ.editIdea(idea);
		return "redirect:/ideas";
	}
	
	@RequestMapping(value="ideas/{id}/delete")
	public String deleteIdea(@PathVariable("id") Long id, HttpSession session) {
		if(session.getAttribute("UserId") == null) {
			return "redirect:/";
		} else if ((Long)session.getAttribute("UserId") == iServ.findIdeaById(id).getCreator().getId()){
			iServ.deleteIdea(iServ.findIdeaById(id));
			return "redirect:/ideas";
		} else {
			return "redirect:/ideas";
		}
	}
	
	@RequestMapping(value="/like/{number}")
	public String like(@PathVariable("number") Long id, HttpSession session) {
		Long user__id = (Long)session.getAttribute("UserId");
		User user = uServ.findById((Long)session.getAttribute("UserId"));
		Idea idea = iServ.findIdeaById(id);
		///
		if(idea.getLikers().contains(user)) {
			iServ.unlike(user, idea);
		} else {
			iServ.like(user, idea);
		}

		return "redirect:/ideas";
	}
	
	//logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	//list by least likes 
	@GetMapping("/ideas/asc")
	public String ascendSort(HttpSession session, Model model) {
		if(session.getAttribute("UserId") == null) {
			return "redirect:/";
		} else {
			User user = uServ.findById((Long)session.getAttribute("UserId"));
			model.addAttribute("loggedIn", user);
			model.addAttribute("ideas", iServ.findIdeasByLeastLikes()); //for now, just spit out all ideas
			return "ideas.jsp";
		}
	}
	
	//list by most likes 
	@GetMapping("/ideas/desc")
	public String descSort(HttpSession session, Model model) {
		if(session.getAttribute("UserId") == null) {
			return "redirect:/";
		} else {
			User user = uServ.findById((Long)session.getAttribute("UserId"));
			model.addAttribute("loggedIn", user);
			model.addAttribute("ideas", iServ.findIdeasByMostLikes()); //for now, just spit out all ideas
			return "ideas.jsp";
		}
	}
	
	
	
	
	

}

