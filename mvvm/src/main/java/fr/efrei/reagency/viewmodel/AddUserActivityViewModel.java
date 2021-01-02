package fr.efrei.reagency.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import fr.efrei.reagency.bo.User;
import fr.efrei.reagency.repository.UserRepository;
import fr.efrei.reagency.view.AddUserActivity;

public final class AddUserActivityViewModel
        extends AndroidViewModel {

    public enum Event {
        ResetForm, DisplayError
    }

    public MutableLiveData<Event> event = new MutableLiveData<>();

    public AddUserActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void saveUser(String userName, String userPhoneNumber, String userAddress,
                         String aboutUser, byte[] avatarBlob) {
        //We display the properties into the logcat
        displayUserEntries(userName, userPhoneNumber, userAddress, aboutUser);

        //We check if all entries are valid (not null and not empty)
        final boolean canAddUser = checkFormEntries(userName, userPhoneNumber, userAddress,
                aboutUser, avatarBlob);

        if (canAddUser == true) {
            //We add the user to the list and we reset the form
            persistUser(userName, userPhoneNumber, userAddress, aboutUser, avatarBlob);
            event.postValue(Event.ResetForm);
        } else {
            //we display a log error and a Toast
            Log.w(AddUserActivity.TAG, "Cannot add the user");
            event.postValue(Event.DisplayError);
        }
    }

    private void persistUser(String userName, String userPhoneNumber, String userAddress,
                             String aboutUser, byte[] avatarBlob) {
        UserRepository.getInstance(getApplication()).addUser(new User(userName, userPhoneNumber,
                userAddress, aboutUser, avatarBlob));
    }

    private boolean checkFormEntries(String userName, String userPhoneNumber, String userAddress,
                                     String aboutUser, byte[] avatarBlob) {
        return TextUtils.isEmpty(userName) == false && TextUtils.isEmpty(userPhoneNumber) == false &&
                TextUtils.isEmpty(userAddress) == false && TextUtils.isEmpty(aboutUser) == false &&
                avatarBlob != null;
    }

    private void displayUserEntries(String userName, String userPhoneNumber, String userAddress,
                                    String aboutUser) {
        Log.d(AddUserActivity.TAG, "name : '" + userName + "'");
        Log.d(AddUserActivity.TAG, "phone number : '" + userPhoneNumber + "'");
        Log.d(AddUserActivity.TAG, "address : '" + userAddress + "'");
        Log.d(AddUserActivity.TAG, "about : '" + aboutUser + "'");
    }

}
