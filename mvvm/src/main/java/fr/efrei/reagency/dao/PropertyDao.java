package fr.efrei.reagency.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import fr.efrei.reagency.bo.Property;
import fr.efrei.reagency.service.IPropertyService;

@Dao
public interface PropertyDao
        extends IPropertyService
{

    @Override
    @Query("SELECT * FROM Property")
    List<Property> getProperties();

    @Override
    @Delete
    void deleteProperty(Property property);

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProperty(Property property);

    @Override
    @Query("SELECT * FROM Property ORDER BY id DESC")
    List<Property> sortPropertiesByIdDes();

    @Override
    @Query("SELECT * FROM Property ORDER BY id ASC")
    List<Property> sortPropertiesByIdAsc();

    @Override
    @Query("SELECT * FROM Property WHERE propertyType='House' ORDER BY id ASC")
    List<Property> sortPropertiesByHouse();

    @Override
    @Query("SELECT * FROM Property WHERE propertyType='Flat' ORDER BY id ASC")
    List<Property> sortPropertiesByFlat();

    @Override
    @Query("SELECT * FROM Property WHERE propertyType='Office' ORDER BY id ASC")
    List<Property> sortPropertiesByOffice();

    @Override
    @Query("SELECT * FROM Property WHERE propertyType='Student Room' ORDER BY id ASC")
    List<Property> sortPropertiesByStudent();

    @Override
    @Query("SELECT * FROM Property ORDER BY propertyPrice ASC")
    List<Property> sortPropertiesByPriceAsc();

    @Override
    @Query("SELECT * FROM Property ORDER BY propertyPrice DESC")
    List<Property> sortPropertiesByPriceDes();

    @Override
    @Query("SELECT * FROM Property ORDER BY propertyPrice ASC")
    List<Property> sortPropertiesBySurfaceAsc();

    @Override
    @Query("SELECT * FROM Property ORDER BY propertyPrice DESC")
    List<Property> sortPropertiesBySurfaceDes();
}