package shop.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.domain.model.User;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService() {};

    public List<User> getAllUsers() {return userRepository.findAll();}

    public User adduser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new ServiceException("save", "user.username.exists");
        }
        if (userRepository.existsByUsername(user.getUsername())){
            throw new ServiceException("save", "user.email.exists");
        }
        return userRepository.save(user);
    }


}
