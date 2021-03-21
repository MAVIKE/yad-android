package ru.m2d.yad.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import ru.m2d.yad_core.core.models.User;
import ru.m2d.yad_core.core.repos.UsersRepo;
import ru.m2d.yad_core.services.mock.repos.MockUsersRepo;

public class UserViewModel extends ViewModel {
    private static final String TAG = UserViewModel.class.getName();
    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
            //noinspection unchecked
            ABSENT.setValue(null);
    }
    private final LiveData<User> userObservable;
    private final MutableLiveData<Integer> userID;
    public ObservableField<User> user = new ObservableField<>();

    public UserViewModel() {
        final UsersRepo userRepository = new MockUsersRepo();
        this.userID = new MutableLiveData<>();
        userObservable = Transformations.switchMap(userID, input -> {
            Log.i(TAG,"ProjectViewModel projectID is " + userID.getValue());
            return userRepository.getById(userID.getValue());
        });
    }

    public UserViewModel(@NonNull UsersRepo userRepository) {
        this.userID = new MutableLiveData<>();
        userObservable = Transformations.switchMap(userID, input -> {
                Log.i(TAG,"ProjectViewModel projectID is " + userID.getValue());
                return userRepository.getById(userID.getValue());
        });
    }

    public LiveData<User> getObservableUser() {
        return userObservable;
    }


    public void setUser(User user) {
        this.user.set(user);
    }

    public void setUserID(int userID) {
        this.userID.setValue(userID);
    }
}
