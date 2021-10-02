package com.store.book.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.book.entities.Book;
import com.store.book.entities.Role;
import com.store.book.entities.User;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type Data feeder.
 */
@UtilityClass
@Slf4j
public class DataFeeder {

  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Gets order details data.
   *
   * @return the order details data
   */
  public List<Book> getBooksData() {
    try {
      return objectMapper
          .readValue(new File(DataFeeder.class.getClassLoader().getResource("json/books.json").getFile())
              , new TypeReference<List<Book>>() {

              });

    } catch (IOException e) {
      log.error("IO exception occured while retrieving books details: {} ", e);
      throw new RuntimeException(e);
    }
  }

  public User formUser() {
    User user = new User();
    user.setUsername("Admin");
    user.setPassword("Admin");
    return user;
  }

  public Role formRole (String roleName) {
    Role role= new Role();
    role.setName(roleName);
    return role;
  }
}
