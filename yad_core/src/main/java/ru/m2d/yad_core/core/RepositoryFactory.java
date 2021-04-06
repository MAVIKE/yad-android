package ru.m2d.yad_core.core;

import android.app.Activity;

import ru.m2d.yad_core.core.repos.LocationRepo;
import ru.m2d.yad_core.core.repos.UsersRepo;

public interface RepositoryFactory {
    UsersRepo newUsersRepo();
    LocationRepo newLocationRepo();
}
