package ru.kata.spring.boot_security.demo.service;



import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> getAllUsers();

    public User getUser(Long id);

    public void save(User user);

    public void updateUser(Long id, User user);

    public void deleteUser(Long id);
    public User getUserByUsername(String username);
}
