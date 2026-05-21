package com.habittracker.controller;

import com.habittracker.service.HabitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HabitController {

    private final HabitService service;

    public HabitController(HabitService service) {
        this.service = service;
    }

    @GetMapping("/")
public String home(Model model) {
    return "redirect:/login";
}

@GetMapping("/home")
public String homePage(Model model) {
    model.addAttribute("habits", service.getAll());
    return "index";
}

    @PostMapping("/add")
    public String addHabit(@RequestParam String name,
                           @RequestParam String category) {
        if (name == null || name.trim().isEmpty()) {
            return "redirect:/";
        }
        service.addHabit(name, category);
       return "redirect:/home";
    }

    @GetMapping("/done/{id}")
    public String done(@PathVariable Long id) {
        service.markDone(id);
        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteHabit(id);
        return "redirect:/home";
    }
    @GetMapping("/reset/{id}")
public String reset(@PathVariable Long id) {
    service.resetStreak(id);
    return "redirect:/admin";
}
    @GetMapping("/login")
public String loginPage() {
    return "login";
}


@PostMapping("/login")
public String login(@RequestParam String username,
                    @RequestParam String password) {

   if(username.equals("user") && password.equals("1234")) {
    return "redirect:/home";
}
if(username.equals("admin") && password.equals("admin")) {
    return "redirect:/admin";
}

    return "login";
}
@GetMapping("/admin")
public String adminPage(Model model) {
    model.addAttribute("habits", service.getAll());
    return "admin";
}

}
