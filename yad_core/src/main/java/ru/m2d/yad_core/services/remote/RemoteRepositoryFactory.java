package ru.m2d.yad_core.services.remote;

import android.app.Activity;

import ru.m2d.yad_core.core.RepositoryFactory;
import ru.m2d.yad_core.core.repos.LocationRepo;
import ru.m2d.yad_core.core.repos.UsersRepo;
import ru.m2d.yad_core.services.device.repos.DeviceLocationRepo;
import ru.m2d.yad_core.services.remote.repos.RemoteService;
import ru.m2d.yad_core.services.remote.repos.RemoteUsersRepo;

public class RemoteRepositoryFactory implements RepositoryFactory {
    private Activity activity;
    private RemoteService remoteService;

    RemoteRepositoryFactory(Activity activity, RemoteService remoteService) {
        this.activity = activity;
        this.remoteService = remoteService;
    }

    @Override
    public UsersRepo newUsersRepo() {
        return new RemoteUsersRepo(remoteService);
    }

    @Override
    public LocationRepo newLocationRepo() {
        return new DeviceLocationRepo(activity);
    }
}
