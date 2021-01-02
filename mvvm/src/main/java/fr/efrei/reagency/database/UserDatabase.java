package fr.efrei.reagency.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import fr.efrei.reagency.bo.User;
import fr.efrei.reagency.dao.UserDao;

@Database(entities = { User.class }, version = 1, exportSchema = false)
public abstract class UserDatabase
    extends RoomDatabase
{

  public abstract UserDao userDao();

}
