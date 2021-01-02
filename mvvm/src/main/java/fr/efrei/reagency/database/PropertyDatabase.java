package fr.efrei.reagency.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import fr.efrei.reagency.bo.Property;
import fr.efrei.reagency.dao.PropertyDao;

@Database(entities = { Property.class }, version = 1, exportSchema = false)
public abstract class PropertyDatabase
        extends RoomDatabase
{

    public abstract PropertyDao propertyDao();

}