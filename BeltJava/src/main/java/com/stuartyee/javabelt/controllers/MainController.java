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
import com.stuartyee.javabelt.validators.IdeaValidator;
import com.stuartyee.javabelt.validators.UserValidator;

@Controller
public class MainController {
	
	@Autowired
	private final UserService uServ;
	@Autowired 
	private final UserValidator uVal;
	@Autowired
	private final IdeaService iServ;
	@Autowired
	private final IdeaValidator iVal;
	
	
	
	public MainController(UserService uServ, UserValidator uVal, IdeaService iServ, IdeaValidator iVal) {
		this.uServ = uServ;
		this.uVal = uVal;
		this.iServ = iServ;
		this.iVal = iVal;
	}
	
	private boolean guestLoggedIn(HttpSession session) {
		if((long)session.getAttribute("UserId") == 0){
			return true;
		} else {
			return false;
		}
	}
	
	private User createGuest() {
		User guest = new User();
		guest.setName("Guest");
		guest.setId((long) 0);
		return guest;
		
	}
	
	private User setUser(HttpSession session) {
		User user;
		if(guestLoggedIn(session)) {
			user = createGuest();
		} else {
			user = uServ.findById((Long)session.getAttribute("UserId"));
		}
		return user;
	}

	//Get and Post pair for Login/Registration Page
	@GetMapping("/")
	public String gotoLoginReg(@ModelAttribute("newUser") User user) {
		
		return "logreg.jsp";
	}
	
	@PostMapping("/")
	public String register(@Valid @ModelAttribute("newUser") User user, BindingResult result, HttpSession session) {
		uVal.validate(user, result);
		if (result.hasErrors()){
			return "logreg.jsp";
		} else {
			uServ.saveUser(user);
			session.setAttribute("UserId", user.getId());
			return "redirect:/ideas"; 
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
	
	//guest login
	@RequestMapping("/guest")
	public String guestLogIn(HttpSession session) {
		session.setAttribute("UserId", (long)0);
		return "redirect:/ideas";
	}
	
	//Got to ideas page
	@GetMapping("/ideas")
	public String gotoIdeas(HttpSession session, Model model) {
		if(session.getAttribute("UserId") == null) {
			return "redirect:/";
		} else {
			User user = setUser(session);
			
			model.addAttribute("loggedIn", user);
			model.addAttribute("ideas", iServ.findAllIdeas()); //for now, just spit out all ideas
			return "ideas.jsp";
		}
	}
	
	//new idea GET and POST pair for MVC form
	@GetMapping("/ideas/new")
	public String draftNewIdea(@ModelAttribute("newIdea") Idea idea, HttpSession session) {
		if((session.getAttribute("UserId") == null) || guestLoggedIn(session)) {
			return "redirect:/";
		} else {
			return "newIdea.jsp";
		}
	}
	
	@PostMapping("/ideas/new")
	public String saveNewIdea(@Valid @ModelAttribute("newIdea") Idea idea, BindingResult result, HttpSession session) {
		iVal.validate(idea, result);
		if(session.getAttribute("UserId") == null) {
			return "redirect:/";
		} else if (result.hasErrors()) {
			return "newIdea.jsp";
		} else {
			User user = setUser(session);
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
			User user = setUser(session);
			model.addAttribute("thisIdea", iServ.findIdeaById(id));
			model.addAttribute("loggedIn", user);

			return "detail.jsp";
		}
	}
	
	@GetMapping("ideas/{id}/edit")
	public String editScreen(@ModelAttribute("thisIdea") Idea idea, @PathVariable("id") Long id, Model model, HttpSession session) {

		Long user__id = (Long)session.getAttribute("UserId");
		Long creator__id = iServ.findIdeaById(id).getCreator().getId();
		if(session.getAttribute("UserId") == null) {
			return "redirect:/";
		} else if (user__id.intValue() == creator__id.intValue()) {
			model.addAttribute("thisIdea", iServ.findIdeaById(id));
			return "editIdea.jsp";
		} else {
			return "redirect:/ideas";
			
		}
	}
	
	@PostMapping("ideas/{id}/edit")
	public String updateIdea(@Valid @ModelAttribute("thisIdea") Idea idea, BindingResult result, @PathVariable("id") Long id) {
		iVal.validate(idea, result);
		if(result.hasErrors()) {
			return "editIdea.jsp";
		} else {
			iServ.editIdea(idea, id);
			return "redirect:/ideas";
		}
	}
	
	@RequestMapping(value="ideas/{id}/delete")
	public String deleteIdea(@PathVariable("id") Long id, HttpSession session) {
		if((session.getAttribute("UserId") == null) || guestLoggedIn(session)) {
			System.out.println("Not logged in to delete");
			return "redirect:/";
		} else if ((Long)session.getAttribute("UserId") == iServ.findIdeaById(id).getCreator().getId()){
			System.out.println("deleting now");
			iServ.deleteIdea(iServ.findIdeaById(id));
			return "redirect:/ideas";
		} else {
			System.out.println("You're not the daddy!");
			return "redirect:/ideas";
		}
	}
	
	@RequestMapping(value="/like/{number}")
	public String like(@PathVariable("number") Long id, HttpSession session) {
		Long user__id = (Long)session.getAttribute("UserId");
		User user = setUser(session);
		Idea idea = iServ.findIdeaById(id);
		///
		if(guestLoggedIn(session)) {
			return "redirect:/ideas";
		} else {
			if(idea.getLikers().contains(user)) {
				iServ.unlike(user, idea);
			} else {
				iServ.like(user, idea);
			}

			return "redirect:/ideas";
		}
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

