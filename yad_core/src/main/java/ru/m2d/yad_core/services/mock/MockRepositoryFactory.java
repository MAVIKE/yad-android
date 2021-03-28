package ru.m2d.yad_core.services.mock;

import ru.m2d.yad_core.core.RepositoryFactory;
import ru.m2d.yad_core.core.repos.LocationRepo;
import ru.m2d.yad_core.core.repos.UsersRepo;
import ru.m2d.yad_core.services.mock.repos.MockLocationRepo;
import ru.m2d.yad_core.services.mock.repos.MockUsersRepo;

public class MockRepositoryFactory implements RepositoryFactory {
    private static UsersRepo usersRepo = new MockUsersRepo();
    private static LocationRepo locationRepo = new MockLocationRepo();

    @Override
    public UsersRepo newUsersRepo() {
        return usersRepo;
    }

    @Override
    public LocationRepo newLocationRepo() {
        return locationRepo;
    }
}
