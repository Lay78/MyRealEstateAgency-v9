package fr.efrei.reagency.viewmodel;

import java.util.List;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import fr.efrei.reagency.bo.User;
import fr.efrei.reagency.repository.UserRepository;

public final class UsersActivityViewModel
        extends AndroidViewModel {

    public enum OrderState {
        SortAsc, SortDes, NoSort
    }

    public MutableLiveData<List<User>> users = new MutableLiveData<>();

    private OrderState currentOrderState = OrderState.SortAsc;

    public UsersActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void updateOrder(OrderState orderState) {
        currentOrderState = orderState;
        loadUsers();
    }

    public void loadUsers() {
        if (currentOrderState == OrderState.SortAsc)
            users.postValue(UserRepository.getInstance(getApplication()).sortUsersByNameAsc());
        else if (currentOrderState == OrderState.SortDes)
            users.postValue(UserRepository.getInstance(getApplication()).sortUsersByNameDes());
        else if (currentOrderState == OrderState.NoSort)
            users.postValue(UserRepository.getInstance(getApplication()).getUsers());

    }

}
