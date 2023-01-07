package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


//Spring MVC + CRUD


    @Controller
    @RequestMapping("/admin")
    public class MyController {


        private final UserService userService;

        @Autowired
        MyController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping()

        public String getAllUsers(Model model) {
            model.addAttribute("users", userService.getAllUsers());
            return "index";
        }


        @GetMapping("/{id}")
        public String show(@PathVariable("id") Long id, Model model) {
            model.addAttribute("user", userService.getUser(id));
            return "show";
        }

        @GetMapping("/new")
        public String addUser(@ModelAttribute("user") User user) {
            return "new";
        }

        @PostMapping()
        public String create(@ModelAttribute("user") User user) {
            userService.save(user);
            return "redirect:/admin";
        }

        @GetMapping("/{id}/edit")
        public String edit(Model model, @PathVariable("id") Long id) {
            model.addAttribute("user", userService.getUser(id));
            return "edit";
        }

        @PatchMapping("/{id}")
        public String update(@ModelAttribute("user") User user,
                             @PathVariable("id") Long id) {
            userService.updateUser(id, user);
            return "redirect:/admin";
        }

        @DeleteMapping("/{id}")
        public String delete(@PathVariable("id") Long id) {
            userService.deleteUser(id);
            return "redirect:/admin";
        }
    }

