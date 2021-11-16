package com.tobrainto.authenticatingldap.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class HomeController {

  @GetMapping("/")
  public String index() {
    return "Welcome to the home page!";
  }

  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  @ResponseBody
  public boolean logout(HttpServletRequest request, Authentication authentication) {
    HttpSession session = request.getSession();
    session.invalidate();
    authentication.setAuthenticated(false);
    return Boolean.TRUE;
  }
}