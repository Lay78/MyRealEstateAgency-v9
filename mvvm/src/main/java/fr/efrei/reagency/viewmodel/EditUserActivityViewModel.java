package fr.efrei.reagency.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import fr.efrei.reagency.bo.User;
import fr.efrei.reagency.repository.UserRepository;
import fr.efrei.reagency.view.EditUserActivity;

import static fr.efrei.reagency.adapter.UsersAdapter.UserViewHolder.curSelectedUser;

public class EditUserActivityViewModel extends AndroidViewModel {

    public enum Event {
        ResetForm, DisplayError
    }

    public MutableLiveData<EditUserActivityViewModel.Event> event = new MutableLiveData<>();

    public EditUserActivityViewModel(@NonNull Application application) {
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
            event.postValue(EditUserActivityViewModel.Event.ResetForm);
        } else {
            //we display a log error and a Toast
            Log.w(EditUserActivity.TAG, "Cannot add the user");
            event.postValue(EditUserActivityViewModel.Event.DisplayError);
        }
    }

    private void persistUser(String userName, String userPhoneNumber, String userAddress,
                             String aboutUser, byte[] avatarBlob) {
        UserRepository.getInstance(getApplication()).deleteUser(curSelectedUser);
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
        Log.d(EditUserActivity.TAG, "name : '" + userName + "'");
        Log.d(EditUserActivity.TAG, "phone number : '" + userPhoneNumber + "'");
        Log.d(EditUserActivity.TAG, "address : '" + userAddress + "'");
        Log.d(EditUserActivity.TAG, "about : '" + aboutUser + "'");
    }

}
