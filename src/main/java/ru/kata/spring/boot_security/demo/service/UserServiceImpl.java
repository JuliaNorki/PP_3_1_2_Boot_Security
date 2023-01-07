package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service //
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
   @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        Optional<User> userOptional=userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }else{
            throw new UsernameNotFoundException(String.format("User id - %s not found", id));
        }

    }

    @Override
    @Transactional
    public void save(User user) {
       user.setPassword(user.getPassword());
       userRepository.saveAndFlush(user);


    }

    @Override
    @Transactional
    public void updateUser(Long id, User user) {
       Optional<User> userOptional = userRepository.findById(id);
       if (userOptional.isPresent()) {
           User userRepos = userOptional.get();
           userRepos.setId(id);
           userRepos.setPassword(user.getPassword());
           userRepos.setUsername(user.getUsername());
           userRepos.setLastname(user.getLastname());
           userRepos.setAge(user.getAge());
           userRepos.setName(user.getName());
           userRepository.save(userRepos);
       }else {
           throw new UsernameNotFoundException(String.format("User  - %s not found", user));

       }

    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
       userRepository.deleteById(id);

    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = getUserByUsername(username);
       if (user == null ) {
           throw new UsernameNotFoundException(String.format("User  - %s not found", username));
       }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getAuthorities());
    }
}


