package fr.efrei.reagency.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import fr.efrei.reagency.bo.Property;
import fr.efrei.reagency.view.PropertyDetailActivity;

public final class PropertyDetailActivityViewModel
        extends ViewModel
{

    public MutableLiveData<Property> property = new MutableLiveData<>();

    public PropertyDetailActivityViewModel(SavedStateHandle savedStateHandle)
    {
        final Property propertyExtra = savedStateHandle.get(PropertyDetailActivity.PROPERTY_EXTRA);
        property.postValue(propertyExtra);
    }

}
