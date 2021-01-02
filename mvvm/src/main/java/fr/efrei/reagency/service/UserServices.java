package fr.efrei.reagency.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.efrei.reagency.bo.User;
import fr.efrei.reagency.bo.User.UserComparator;

final public class UserServices
    implements IUserService
{

  private final List<User> users = new ArrayList<>();

  @Override
  public List<User> getUsers()
  {
    return users;
  }

  @Override
  public void deleteUser(User user)
  {
    users.remove(user);
  }

  @Override
  public void addUser(User user)
  {
    users.add(user);
  }

  @Override
  public List<User> sortUsersByNameDes()
  {
    final List<User> sortedUsers = new ArrayList<>(users);

    Collections.sort(sortedUsers, new UserComparator());

    return sortedUsers;
  }

  @Override
  public List<User> sortUsersByNameAsc()
  {
    final List<User> sortedUsers = new ArrayList<>(users);

    Collections.sort(sortedUsers, new UserComparator());

    return sortedUsers;
  }
}
