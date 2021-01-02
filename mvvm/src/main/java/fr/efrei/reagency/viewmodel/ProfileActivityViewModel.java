package fr.efrei.reagency.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.efrei.reagency.bo.User;
import fr.efrei.reagency.preferences.AppPreferences;
import fr.efrei.reagency.repository.UserRepository;

public final class ProfileActivityViewModel
        extends AndroidViewModel {

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<List<User>> users = new MutableLiveData<>();

    public ProfileActivityViewModel(@NonNull Application application) {
        super(application);

        loadUserName();
    }

    public void saveLogin(String name) {
        //We save only if there is something to save
        if (TextUtils.isEmpty(name) == false) {
            AppPreferences.saveUserLogin(getApplication(), name);
        }
    }

    private void loadUserName() {
        //We retrieve the name store into the shared preferences
        final String userLogin = AppPreferences.getUserLogin(getApplication());

        //if the name is not null we restore it
        if (TextUtils.isEmpty(userLogin) == false) {
            name.postValue(userLogin);
        }
    }

    public void loadUsers() {
        users.postValue(UserRepository.getInstance(getApplication()).sortUsersByNameAsc());
    }
}
