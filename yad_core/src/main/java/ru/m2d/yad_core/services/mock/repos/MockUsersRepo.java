package ru.m2d.yad_core.services.mock.repos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.m2d.yad_core.core.models.User;
import ru.m2d.yad_core.core.repos.UsersRepo;

public class MockUsersRepo implements UsersRepo {

    public MockUsersRepo(){}

    @Override
    public LiveData<User> getById(int id) {
        final MutableLiveData<User> data = new MutableLiveData<>();
        User user = new User();
        user.name = "Helloskajdalskjdlask";
        user.id = 12;
        data.setValue(user);
        return data;
    }

    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
