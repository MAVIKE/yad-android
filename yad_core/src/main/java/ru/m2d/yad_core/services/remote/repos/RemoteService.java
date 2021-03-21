package ru.m2d.yad_core.services.remote.repos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.m2d.yad_core.services.remote.models.RawUser;

public interface RemoteService {
    String HTTP_API_REMOTE_URL = "https://our.beautiful.url/";

    @GET("users/{user}")
    Call<RawUser> getUserById(@Path("user") int userId);
}
