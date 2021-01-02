package fr.efrei.reagency.service;

import java.util.List;

import fr.efrei.reagency.bo.Property;

//Interface used in order to implement correctly the repository pattern
public interface IPropertyService
{

    /**
     * Get all properties
     *
     * @return {@link List}
     */
    List<Property> getProperties();

    /**
     * Deletes an property
     *
     * @param property
     */
    void deleteProperty(Property property);

    /**
     * Add an property
     *
     * @param property
     */
    void addProperty(Property property);

    /**
     * Get all properties sorted by Id Descending
     *
     * @return {@link List}
     */
    List<Property> sortPropertiesByIdDes();

    /**
     * Get all properties sorted by Id Ascending
     *
     * @return {@link List}
     */
    List<Property> sortPropertiesByIdAsc();

    /**
     * Get all properties equal House
     *
     * @return {@link List}
     */
    List<Property> sortPropertiesByHouse();

    /**
     * Get all properties equal Flat
     *
     * @return {@link List}
     */
    List<Property> sortPropertiesByFlat();

    /**
     * Get all properties equal Office
     *
     * @return {@link List}
     */
    List<Property> sortPropertiesByOffice();

    /**
     * Get all properties equal Student Room
     *
     * @return {@link List}
     */
    List<Property> sortPropertiesByStudent();

    /**
     * Get all properties sorted by Price Ascending
     *
     * @return {@link List}
     */
    List<Property> sortPropertiesByPriceAsc();

    /**
     * Get all properties sorted by Price Descending
     *
     * @return {@link List}
     */
    List<Property> sortPropertiesByPriceDes();

    /**
     * Get all properties sorted by Surface Ascending
     *
     * @return {@link List}
     */
    List<Property> sortPropertiesBySurfaceAsc();

    /**
     * Get all properties sorted by Surface Descending
     *
     * @return {@link List}
     */
    List<Property> sortPropertiesBySurfaceDes();
}
