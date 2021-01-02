package fr.efrei.reagency.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import fr.efrei.reagency.bo.Property;
import fr.efrei.reagency.repository.PropertyRepository;
import fr.efrei.reagency.view.EditPropertyActivity;
import fr.efrei.reagency.view.AddUserActivity;

import static fr.efrei.reagency.adapter.PropertiesAdapter.PropertyViewHolder.curSelectedProperty;

public class EditPropertyActivityViewModel
        extends AndroidViewModel {
    public enum Event
    {
        ResetForm, DisplayError
    }
    public MutableLiveData<EditPropertyActivityViewModel.Event> event = new MutableLiveData<>();

    public EditPropertyActivityViewModel(@NonNull Application application)
    {
        super(application);
    }

    public void saveProperty(String propertyType, int propertyPrice, int propertySurface,
                             int propertyRoom, String propertyDescription,
                             String propertyAddress, String propertyLatitude,
                             String propertyLongitude, String propertyDateCreation,
                             String propertyDateUpdate, String propertyDateSold,
                             String propertyStatus,
                             String propertyAgentName, byte[] imageBlob)
    {
        //We display the properties into the logcat
        displayPropertyEntries(propertyType, propertyPrice, propertySurface, propertyRoom,
                propertyDescription, propertyAddress, propertyLatitude, propertyLongitude,
                propertyDateCreation, propertyDateUpdate, propertyDateSold,
                propertyStatus, propertyAgentName);

        //TEST A-LAY A ENLEVER
        //We check if all entries are valid (not null and not empty)
        final boolean canAddProperty = checkFormEntries(propertyType, propertyPrice,
                propertySurface, propertyRoom, propertyDescription, propertyAddress,
                propertyLatitude, propertyLongitude, propertyDateCreation,
                propertyDateUpdate, propertyDateSold, propertyStatus, propertyAgentName, imageBlob);
        if (canAddProperty == true)
        {
            //We add the property to the list and we reset the form
            persistProperty(propertyType, propertyPrice, propertySurface, propertyRoom,
                    propertyDescription, propertyAddress, propertyLatitude, propertyLongitude,
                    propertyDateCreation, propertyDateUpdate, propertyDateSold,
                    propertyStatus, propertyAgentName, imageBlob);
            event.postValue(EditPropertyActivityViewModel.Event.ResetForm);
        }
        else
        {
            //we display a log error and a Toast
            Log.w(AddUserActivity.TAG, "Cannot add the property");
            event.postValue(EditPropertyActivityViewModel.Event.DisplayError);
        }
    }

    private void persistProperty(String propertyType, int propertyPrice, int propertySurface,
                                 int propertyRoom, String propertyDescription,
                                 String propertyAddress, String propertyLatitude,
                                 String propertyLongitude, String propertyDateCreation,
                                 String propertyDateUpdate, String propertyDateSold,
                                 String propertyStatus,
                                 String propertyAgentName, byte[] imageBlob)
    {
        PropertyRepository.getInstance(getApplication()).deleteProperty(curSelectedProperty);
        PropertyRepository.getInstance(getApplication()).addProperty(new Property(propertyType,
                propertyPrice, propertySurface,
                propertyRoom, propertyDescription, propertyAddress, propertyLatitude,
                propertyLongitude, propertyDateCreation, propertyDateUpdate, propertyDateSold,
                propertyStatus, propertyAgentName, imageBlob));
    }

    private boolean checkFormEntries(String propertyType, int propertyPrice,
                                     int propertySurface, int propertyRoom,
                                     String propertyDescription, String propertyAddress,
                                     String propertyLatitude, String propertyLongitude,
                                     String propertyDateCreation, String propertyDateUpdate,
                                     String propertyDateSold,
                                     String propertyStatus, String propertyAgentName,
                                     byte[] imageBlob)
    {
        boolean ret = TextUtils.isEmpty(propertyType) == false &&
                propertyPrice != 0 &&
                propertySurface != 0 &&
                propertyRoom != 0 &&
                TextUtils.isEmpty(propertyDescription) == false &&
                TextUtils.isEmpty(propertyAddress) == false &&
                TextUtils.isEmpty(propertyLatitude) == false &&
                TextUtils.isEmpty(propertyLongitude) == false &&
                TextUtils.isEmpty(propertyDateCreation) == false &&
                //TextUtils.isEmpty(propertyDateUpdate) == false &&
                TextUtils.isEmpty(propertyStatus) == false &&
                TextUtils.isEmpty(propertyAgentName) == false &&
                imageBlob != null;
        return ret;
    }

    private void displayPropertyEntries(String propertyType, int propertyPrice,
                                        int propertySurface, int propertyRoom,
                                        String propertyDescription, String propertyAddress,
                                        String propertyLatitude, String propertyLongitude,
                                        String propertyDateCreation, String propertyDateUpdate,
                                        String propertyDateSold,
                                        String propertyStatus, String propertyAgentName)
    {
        Log.d(EditPropertyActivity.TAG, "Type : '" + propertyType + "'");
        Log.d(EditPropertyActivity.TAG, "Price : '" + propertyPrice + "'");
        Log.d(EditPropertyActivity.TAG, "Surface : '" + propertySurface + "'");
        Log.d(EditPropertyActivity.TAG, "Rooms : '" + propertyRoom + "'");
        Log.d(EditPropertyActivity.TAG, "Description : '" + propertyDescription + "'");
        Log.d(EditPropertyActivity.TAG, "Address : '" + propertyAddress + "'");
        Log.d(EditPropertyActivity.TAG, "Latitude : '" + propertyLatitude + "'");
        Log.d(EditPropertyActivity.TAG, "Longitude : '" + propertyLongitude + "'");
        Log.d(EditPropertyActivity.TAG, "Creation Date : '" + propertyDateCreation + "'");
        Log.d(EditPropertyActivity.TAG, "Update Date : '" + propertyDateUpdate + "'");
        Log.d(EditPropertyActivity.TAG, "Sold Date : '" + propertyDateSold + "'");
        Log.d(EditPropertyActivity.TAG, "Status : '" + propertyStatus + "'");
        Log.d(EditPropertyActivity.TAG, "Agent Name : '" + propertyAgentName + "'");
    }

}
