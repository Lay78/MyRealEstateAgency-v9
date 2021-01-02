package fr.efrei.reagency.repository;

import java.util.List;

import android.content.Context;

import androidx.room.Room;

import fr.efrei.reagency.bo.User;
import fr.efrei.reagency.database.UserDatabase;

//This class implement the singleton pattern
public final class UserRepository
{

  private static volatile UserRepository instance;

  // We accept the "out-of-order writes" case
  public static UserRepository getInstance(Context context)
  {
    if (instance == null)
    {
      synchronized (UserRepository.class)
      {
        if (instance == null)
        {
          instance = new UserRepository(context);
        }
      }
    }

    return instance;
  }

  private final UserDatabase userDatabase;

  private UserRepository(Context context)
  {
    userDatabase = Room.databaseBuilder(context, UserDatabase.class, "user-database").allowMainThreadQueries().build();
  }

  public List<User> getUsers()
  {
    return userDatabase.userDao().getUsers();
  }

  public void deleteUser(User user)
  {
    userDatabase.userDao().deleteUser(user);
  }

  public void addUser(User user)
  {
    userDatabase.userDao().addUser(user);
  }

  public List<User> sortUsersByNameDes()
  {
    return userDatabase.userDao().sortUsersByNameDes();
  }
  public List<User> sortUsersByNameAsc()
  {
    return userDatabase.userDao().sortUsersByNameAsc();
  }

}
