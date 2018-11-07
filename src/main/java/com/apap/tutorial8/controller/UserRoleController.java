package com.apap.tutorial8.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.addUser(user);
		return "home";
	}

	@RequestMapping(value = "/updatepassword", method = RequestMethod.GET)
	private String updatePassword() {
		return "updatePassword";
	}

	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
	private String updatePasswordSubmit(@RequestParam(value = "passwordLama") String passwordLama,
			@RequestParam(value = "passwordBaru") String passwordBaru,
			@RequestParam(value = "konfirmasiPassword") String konfirmasiPassword, HttpServletRequest request,
			Model model) {

		UserRoleModel user = userService
				.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (passwordBaru.equals(konfirmasiPassword)) {

			if (passwordBaru.length() >= 8) {
				boolean hurufAngka = passwordBaru.matches("[a-zA-Z ]*\\d+.*");
	
				if (hurufAngka) {
					if (userService.cekPassword(passwordLama, user.getPassword())) {
						user.setPassword(passwordBaru);
						userService.addUser(user);

						model.addAttribute("message", "success");
						return "updatePassword";
					}

					else {
						model.addAttribute("message", "tidakcocok");
						return "updatePassword";
					}
				}
				
				else {
					model.addAttribute("message", "tidakhurufangka");
					return "updatePassword";
				}

			}

			else {
				model.addAttribute("message", "salahketentuan");
				return "updatePassword";
			}
		}

		else {
			model.addAttribute("message", "konfirmasi");
			return "updatePassword";

		}
	}
}
