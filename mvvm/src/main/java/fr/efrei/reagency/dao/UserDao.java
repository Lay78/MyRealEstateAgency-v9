package fr.efrei.reagency.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import fr.efrei.reagency.bo.User;
import fr.efrei.reagency.service.IUserService;

@Dao
public interface UserDao
  extends IUserService
{

  @Override
  @Query("SELECT * FROM User")
  List<User> getUsers();

  @Override
  @Delete
  void deleteUser(User user);

  @Override
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void addUser(User user);

  @Override
  @Query("SELECT * FROM User ORDER BY name DESC")
  List<User> sortUsersByNameDes();

  @Override
  @Query("SELECT * FROM User ORDER BY name ASC")
  List<User> sortUsersByNameAsc();

}
