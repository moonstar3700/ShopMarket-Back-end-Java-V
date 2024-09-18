package shop.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import shop.domain.model.User;

import java.util.List;
import java.util.Optional;

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


    public User updateUser(User user, long id) {
        /** check whether the username and email already exist on another profile (id)*/
        if (userRepository.existsByUsernameAndExcludeId(user.getUsername(), user.getId())) throw new ServiceException("update", "user.username.exists");
        if (userRepository.existsByEmailAndExcludeId(user.getEmail(), user.getId())) throw new ServiceException("update", "user.email.exists");
        user.setId(id);
        return userRepository.save(user);
    }

    public boolean getUserById(long id) {
        return userRepository.existsById(id);
    }

    public User deleteById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ServiceException("delete", "user.not.exists"));
        userRepository.delete(user);
        return user;
    }
}
