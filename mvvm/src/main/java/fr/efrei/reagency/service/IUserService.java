package fr.efrei.reagency.service;

import java.util.List;

import fr.efrei.reagency.bo.User;

//Interface used in order to implement correctly the repository pattern
public interface IUserService
{

  /**
   * Get all users
   *
   * @return {@link List}
   */
  List<User> getUsers();

  /**
   * Deletes an user
   *
   * @param user
   */
  void deleteUser(User user);

  /**
   * Add an user
   *
   * @param user
   */
  void addUser(User user);

  /**
   * Get all users sorted by name Descending
   *
   * @return {@link List}
   */
  List<User> sortUsersByNameDes();

  /**
   * Get all users sorted by name Ascending
   *
   * @return {@link List}
   */
  List<User> sortUsersByNameAsc();
}
