package ru.m2d.yad_core.services.remote.repos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.m2d.yad_core.core.models.User;
import ru.m2d.yad_core.core.repos.UsersRepo;
import ru.m2d.yad_core.services.remote.models.RawUser;

public class RemoteUsersRepo implements UsersRepo {
    private RemoteService remoteService;

    public RemoteUsersRepo(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Override
    public LiveData<User> getById(int userId) {
        final MutableLiveData<User> data = new MutableLiveData<>();

        remoteService.getUserById(userId).enqueue(new Callback<RawUser>() {
            @Override
            public void onResponse(Call<RawUser> call, Response<RawUser> response) {
                // TODO: Implement conversion from RawUser to User
                //data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RawUser> call, Throwable t) {
                // TODO: better error handling
                data.setValue(null);
            }
        });

        return data;
    }

}

