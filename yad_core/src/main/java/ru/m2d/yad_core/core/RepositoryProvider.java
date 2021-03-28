package ru.m2d.yad_core.core;

import ru.m2d.yad_core.core.repos.LocationRepo;
import ru.m2d.yad_core.core.repos.UsersRepo;

public class RepositoryProvider {
    private RepositoryFactory repositoryFactory;
    private UsersRepo usersRepo;
    private LocationRepo locationRepo;
    private static RepositoryProvider instance = new RepositoryProvider();

    static RepositoryProvider getInstance() {
        return instance;
    }

    static void setFactory(RepositoryFactory factory) {
        instance.repositoryFactory = factory;
    }

    UsersRepo getUsersRepo() {
        if (usersRepo == null) {
            if (repositoryFactory == null) {
                return null;
            }
            usersRepo = repositoryFactory.newUsersRepo();
        }
        return usersRepo;
    }

    LocationRepo getLocationRepo() {
        if (locationRepo == null) {
            if (repositoryFactory == null) {
                return null;
            }
            locationRepo = repositoryFactory.newLocationRepo();
        }
        return locationRepo;

    }
}
