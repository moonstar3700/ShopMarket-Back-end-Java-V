package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import shop.domain.model.User;
import shop.domain.service.ServiceException;
import shop.domain.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/home")
    public String home(){
        return "user controller";
    }

    @GetMapping("/overview")
    public Iterable<User> getAll() { return userService.getAllUsers(); }

    @PutMapping("/add")
    public Iterable<User> addUser(@Valid @RequestBody User user) {
        userService.adduser(user);
        return this.getAll();}

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable long id, @Valid @RequestBody User user){
        if (!userService.getUserById(id)) throw new ServiceException("update", "user.not.exist");
        try {
            return userService.updateUser(user, id);
        } catch (ServiceException ex){
            throw new ServiceException("update", ex.getMessage());
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ServiceException.class, ResponseStatusException.class})
    public Map<String, String> handleValidationExceptions(Exception ex, Locale locale){
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = messageSource.getMessage(error.getDefaultMessage(), null, locale);
                errors.put(fieldName, errorMessage);
            });
        }
        else if (ex instanceof ServiceException) {
            String error = messageSource.getMessage(ex.getMessage(), null, locale);
            errors.put(((ServiceException) ex).getAction(), error);
        }
        else {
            String errorMessage = messageSource.getMessage(ex.getCause().getMessage(), null, locale);
            errors.put(((ResponseStatusException)ex).getReason(), errorMessage);
        }
        return errors;
    }

}
