package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.domain.model.User;
import shop.domain.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(){
        return "user controller";
    }

    @GetMapping("/overview")
    public Iterable<User> getAll() { return userService.getAllUsers(); }
}
