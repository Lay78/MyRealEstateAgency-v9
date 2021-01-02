package fr.efrei.reagency.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import fr.efrei.reagency.bo.Property;
import fr.efrei.reagency.repository.PropertyRepository;
import fr.efrei.reagency.view.AddPropertyActivity;
import fr.efrei.reagency.view.AddUserActivity;


public class AddPropertyActivityViewModel
        extends AndroidViewModel
{
    public enum Event
    {
        ResetForm, DisplayError
    }
    public MutableLiveData<AddPropertyActivityViewModel.Event> event = new MutableLiveData<>();

    public AddPropertyActivityViewModel(@NonNull Application application)
    {
        super(application);
    }

    public void saveProperty(String propertyType, int propertyPrice, int propertySurface,
                             int propertyRoom, String propertyDescription,
                             String propertyAddress, String propertyLatitude,
                             String propertyLongitude, String propertyDateCreation,
                             String propertyDateUpdate, String propertyDateSold, String propertyStatus,
                             String propertyAgentName, byte[] imageBlob)
    {
        //We display the properties into the logcat
        displayPropertyEntries(propertyType, propertyPrice, propertySurface, propertyRoom,
                propertyDescription, propertyAddress, propertyLatitude, propertyLongitude,
                propertyDateCreation, propertyDateUpdate, propertyDateSold, propertyStatus, propertyAgentName);

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
            event.postValue(Event.ResetForm);
        }
        else
        {
            //we display a log error and a Toast
            Log.w(AddUserActivity.TAG, "Cannot add the property");
            event.postValue(Event.DisplayError);
        }
    }

    private void persistProperty(String propertyType, int propertyPrice, int propertySurface,
                                 int propertyRoom, String propertyDescription,
                                 String propertyAddress, String propertyLatitude,
                                 String propertyLongitude, String propertyDateCreation,
                                 String propertyDateUpdate, String propertyDateSold,
                                 String propertyStatus, String propertyAgentName, byte[] imageBlob)
    {
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
        Log.d(AddPropertyActivity.TAG, "Type : '" + propertyType + "'");
        Log.d(AddPropertyActivity.TAG, "Price : '" + propertyPrice + "'");
        Log.d(AddPropertyActivity.TAG, "Surface : '" + propertySurface + "'");
        Log.d(AddPropertyActivity.TAG, "Rooms : '" + propertyRoom + "'");
        Log.d(AddPropertyActivity.TAG, "Description : '" + propertyDescription + "'");
        Log.d(AddPropertyActivity.TAG, "Address : '" + propertyAddress + "'");
        Log.d(AddPropertyActivity.TAG, "Latitude : '" + propertyLatitude + "'");
        Log.d(AddPropertyActivity.TAG, "Longitude : '" + propertyLongitude + "'");
        Log.d(AddPropertyActivity.TAG, "Creation Date : '" + propertyDateCreation + "'");
        Log.d(AddPropertyActivity.TAG, "Update Date : '" + propertyDateUpdate + "'");
        Log.d(AddPropertyActivity.TAG, "Update Date : '" + propertyDateSold + "'");
        Log.d(AddPropertyActivity.TAG, "Status : '" + propertyStatus + "'");
        Log.d(AddPropertyActivity.TAG, "Agent Name : '" + propertyAgentName + "'");
    }

}
