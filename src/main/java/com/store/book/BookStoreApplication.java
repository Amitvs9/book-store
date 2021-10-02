package com.store.book;

import com.store.book.entities.Role;
import com.store.book.entities.User;
import com.store.book.repository.BookRepository;
import com.store.book.repository.RoleRepository;
import com.store.book.service.UserService;
import com.store.book.util.DataFeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;

@SpringBootApplication
public class BookStoreApplication implements CommandLineRunner {

    private final UserService userService;
    private final BookRepository bookRepository;
    private final RoleRepository roleRepository;

    public BookStoreApplication(UserService userService, BookRepository bookRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //Create two default roles
        Role roleAdmin = DataFeeder.formRole("ADMIN");
        Role roleUser = DataFeeder.formRole("USER");
        if(CollectionUtils.isEmpty(roleRepository.findAll())){
            roleRepository.saveAll(Arrays.asList(roleAdmin, roleUser));
        }

        //Create one default admin user
        if (!userService.getByUsername(DataFeeder.formUser()).isPresent()) {
            Role userRole = roleRepository.findByName("ADMIN");
            User user = DataFeeder.formUser();
            user.addRole(userRole);
            userService.createUser(user);
        }

        //Load Books data only one time in memo database.
        if (CollectionUtils.isEmpty(bookRepository.findAll())) {
           bookRepository.saveAll(DataFeeder.getBooksData());
        }
    }


}
