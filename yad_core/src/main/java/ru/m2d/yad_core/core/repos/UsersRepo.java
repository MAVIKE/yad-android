package ru.m2d.yad_core.core.repos;

import androidx.lifecycle.LiveData;

import ru.m2d.yad_core.core.models.User;

public interface UsersRepo {
    LiveData<User> getById(int id);
}
