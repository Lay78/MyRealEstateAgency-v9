package fr.efrei.reagency.tools;

import fr.efrei.reagency.adapter.PropertiesAdapter;
import fr.efrei.reagency.bo.Property;

public class PropertyViewHolderGroup {
    private int position;
    private PropertiesAdapter.PropertyViewHolder viewHolder;
    private Property property;

    public PropertyViewHolderGroup(int position, PropertiesAdapter.PropertyViewHolder viewHolder, Property property) {
        this.position = position;
        this.viewHolder = viewHolder;
        this.property = property;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public PropertiesAdapter.PropertyViewHolder getViewHolder() {
        return viewHolder;
    }

    public void setViewHolder(PropertiesAdapter.PropertyViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
