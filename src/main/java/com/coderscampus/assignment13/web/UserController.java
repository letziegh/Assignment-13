package com.coderscampus.assignment13.web;

import java.util.Arrays;
import java.util.Set;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;
	
	@GetMapping("/register")
	public String getCreateUser (ModelMap model) {
		
		model.put("user", new User());
		
		return "register";
	}
	
	@PostMapping("/register")
	public String postCreateUser (User user) {
		System.out.println(user);
		userService.saveUser(user);
		return "redirect:/register";
	}
	
	@GetMapping("/users")
	public String getAllUsers (ModelMap model) {
		Set<User> users = userService.findAll();
		
		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}
		
		return "users";
	}
	
	@GetMapping("/users/{userId}")
	public String getOneUser (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		model.put("users", Arrays.asList(user));
		model.put("user", user);
		return "users";
	}
	
	@PostMapping("/users/{userId}")
	public String postOneUser (User user) {
		//userService.saveUser(user);
		userService.updateUser(user);
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		return "redirect:/users/"+user.getUserId();
	}
	
	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser (@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}
	@GetMapping("/users/{userId}/accounts/new")
	public String showNewAccountForm(@PathVariable Long userId, Model model) {
		model.addAttribute("userId", userId); // Pass the userId to the form
		model.addAttribute("account", null); // New account object for the form
		return "account-form"; // Account creation form template (Thymeleaf view)
	}
	@PostMapping("/users/{userId}/accounts/new")
	public String createNewAccount(@PathVariable Long userId, @RequestParam("name") String accountName) {
		accountService.createNewAccount(userId, accountName);
		return "redirect:/users/" + userId; // Redirect back to the user page after creation
	}
//	@PostMapping("/users/{userId}/accounts/new")
//	public String createNewAccount(@PathVariable Long userId, @RequestParam(value = "name", required = false) String name, Model model) {
//		if (name == null || name.isEmpty()) {
//			// No name provided, so show the account creation page
//			model.addAttribute("userId", userId);
//			model.addAttribute("account", null); // Ensure it's null for the creation page
//			return "account-form"; // Return the account form template
//		}

		// If the name is provided, create the account
//		accountService.createNewAccount(userId, name);
//		return "redirect:/users/" + userId;
//	}

	//	@PostMapping("/users/{userId}/accounts/new")
//	public String createNewAccount(@PathVariable Long userId, @RequestParam (value = "name", required = false) String name ) {
//		accountService.createNewAccount(userId, name);
//		//model.addAttribute("user", userService.findById(userId));
//		return "redirect:/users/" + userId;
//	}
	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String getAccountDetails(@PathVariable Long userId, @PathVariable Long accountId, Model model) {
		Account account = accountService.findAccountByAccountId(accountId);
		model.addAttribute("account", account);
		return "account-form";
	}

	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String updateAccountName(@PathVariable Long userId, @PathVariable Long accountId, @RequestParam String name) {
		accountService.updateAccountName(accountId, name);
		return "redirect:/users/" + userId ;
	}

}
