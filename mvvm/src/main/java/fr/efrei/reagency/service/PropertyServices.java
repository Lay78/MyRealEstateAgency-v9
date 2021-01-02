package fr.efrei.reagency.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.efrei.reagency.bo.Property;
import fr.efrei.reagency.bo.Property.PropertyComparator;

final public class PropertyServices
        implements IPropertyService
{

    private final List<Property> properties = new ArrayList<>();

    @Override
    public List<Property> getProperties()
    {
        return properties;
    }

    @Override
    public void deleteProperty(Property property)
    {
        properties.remove(property);
    }

    @Override
    public void addProperty(Property property)
    {
        properties.add(property);
    }

    @Override
    public List<Property> sortPropertiesByIdDes()
    {
        final List<Property> sortedProperties = new ArrayList<>(properties);
        Collections.sort(sortedProperties, new PropertyComparator());
        return sortedProperties;
    }
    @Override
    public List<Property> sortPropertiesByIdAsc()
    {
        final List<Property> sortedProperties = new ArrayList<>(properties);
        Collections.sort(sortedProperties, new PropertyComparator());
        return sortedProperties;
    }
    @Override
    public List<Property> sortPropertiesByHouse()
    {
        final List<Property> sortedProperties = new ArrayList<>(properties);
        Collections.sort(sortedProperties, new PropertyComparator());
        return sortedProperties;
    }
    @Override
    public List<Property> sortPropertiesByFlat()
    {
        final List<Property> sortedProperties = new ArrayList<>(properties);
        Collections.sort(sortedProperties, new PropertyComparator());
        return sortedProperties;
    }
    @Override
    public List<Property> sortPropertiesByOffice()
    {
        final List<Property> sortedProperties = new ArrayList<>(properties);
        Collections.sort(sortedProperties, new PropertyComparator());
        return sortedProperties;
    }
    @Override
    public List<Property> sortPropertiesByStudent()
    {
        final List<Property> sortedProperties = new ArrayList<>(properties);
        Collections.sort(sortedProperties, new PropertyComparator());
        return sortedProperties;
    }
    @Override
    public List<Property> sortPropertiesByPriceAsc()
    {
        final List<Property> sortedProperties = new ArrayList<>(properties);
        Collections.sort(sortedProperties, new PropertyComparator());
        return sortedProperties;
    }
    @Override
    public List<Property> sortPropertiesByPriceDes()
    {
        final List<Property> sortedProperties = new ArrayList<>(properties);
        Collections.sort(sortedProperties, new PropertyComparator());
        return sortedProperties;
    }
    @Override
    public List<Property> sortPropertiesBySurfaceAsc()
    {
        final List<Property> sortedProperties = new ArrayList<>(properties);
        Collections.sort(sortedProperties, new PropertyComparator());
        return sortedProperties;
    }
    @Override
    public List<Property> sortPropertiesBySurfaceDes()
    {
        final List<Property> sortedProperties = new ArrayList<>(properties);
        Collections.sort(sortedProperties, new PropertyComparator());
        return sortedProperties;
    }


}
