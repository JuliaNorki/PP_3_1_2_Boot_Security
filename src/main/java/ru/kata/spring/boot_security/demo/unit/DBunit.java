package ru.kata.spring.boot_security.demo.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class DBunit {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public DBunit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct //команда чтоб приложение запускалось работает автоматически
    public void initDbUsers() {

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        Set<Role> adminRoles = new HashSet<>();
        Collections.addAll(adminRoles, roleAdmin, roleUser);
        roleService.save(adminRoles);

        User admin = new User();
        admin.setUsername("admin");
        admin.setName("admin");
        admin.setPassword("admin");

        admin.setLastname("admin");
        admin.setAge(21L);
        admin.setUsername("admin@mail.ru");
        admin.setRoles(new String[]{"ROLE_ADMIN", "ROLE_USER"});

        userService.save(admin);

        User user = new User();
        Set<Role> userRoles = new HashSet<>();
        Collections.addAll(userRoles, roleUser);
        user.setUsername("user");
        user.setName("user");

        user.setPassword("user");
        user.setLastname("user");
        user.setAge(22L);
        user.setUsername("user@mail.ru");
        user.setRoles(new String[]{"ROLE_USER"});
        userService.save(user);


    }
}

